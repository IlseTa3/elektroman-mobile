package be.ucll.jmelektromanex.database;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.ucll.jmelektromanex.LocalDateConverter;
import be.ucll.jmelektromanex.dao.UserDao;
import be.ucll.jmelektromanex.dao.WorkOrderDao;
import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.entities.UserWithWorkOrders;
import be.ucll.jmelektromanex.entities.WorkOrder;

@Database(entities = {User.class, WorkOrder.class},version = 3,exportSchema = false)
@TypeConverters({LocalDateConverter.class})
public abstract class JmElektroManEx_database extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract WorkOrderDao workOrderDao();

    private static volatile JmElektroManEx_database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static JmElektroManEx_database getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (JmElektroManEx_database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    JmElektroManEx_database.class,"elektroman_database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                    JmElektroManEx_database.dbWriteExecutor.execute(()-> {
                        INSTANCE.clearAllTables();
                    });
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    INSTANCE.clearAllTables();
                    //1 hardcoded User
                    User user = new User();
                    user.setFirstName("Ilse");
                    user.setLastName("Tastenhoye");
                    user.setUsername("test");
                    user.setPassword("test123");
                    //5 Hardcoded Workorders
                    WorkOrder wo1 = new WorkOrder("Brussel", "Koffie machine", "14", "De Laet Kenny",true
                            ,"Koffie machine lekt water","Adapter werd vervangen");
                    WorkOrder wo2 = new WorkOrder("Antwerpen", "Microwave", "48", "Laeremans Olaf",false);
                    WorkOrder wo3 = new WorkOrder("Gent", "Deepfreezer", "12", "Stockmans Maria"
                            ,true,"diepvriezer werkt niet op volle kracht en producten zijn niet diepgevroren"
                            ,"Motor van de diepvriezer moet vervangen worden wegens versleten. Er werd een nieuwe besteld.");
                    WorkOrder wo4 = new WorkOrder("Ekeren", "Refrigerator", "48", "Kelchtermans Eva",false,"");
                    WorkOrder wo5 = new WorkOrder("Oudergem", "Koffie machine", "14", "Pelmans Gustave",false,"");
                    INSTANCE.userDao().insertUser(user);
                    INSTANCE.workOrderDao().insertWorkOrder(wo1);
                    INSTANCE.workOrderDao().insertWorkOrder(wo2);
                    INSTANCE.workOrderDao().insertWorkOrder(wo3);
                    INSTANCE.workOrderDao().insertWorkOrder(wo4);
                    INSTANCE.workOrderDao().insertWorkOrder(wo5);

                }
            });
        }
    };

    //Executor wordt uitgevoerd op de achtergrond
    public void executeOnBackgroundThread(Runnable runnable){
        dbWriteExecutor.execute(runnable);
    }
}
