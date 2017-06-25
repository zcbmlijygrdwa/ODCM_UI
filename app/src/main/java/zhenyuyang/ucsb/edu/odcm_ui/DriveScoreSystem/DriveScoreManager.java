package zhenyuyang.ucsb.edu.odcm_ui.DriveScoreSystem;


/**
 * Created by Zhenyu on 2017-06-25.
 */

public class DriveScoreManager {
    private static DriveScoreManager instance;
    private volatile float leftRightScore;
    private volatile float forwardScore;
    private volatile float backwardScore;
    private Thread updateThread;
    private DriveScoreManager.DriveScoreChangeListener driveScoreChangeListener;
    private int updatePeriod = 2;  //seconds

    public interface DriveScoreChangeListener{
        void onDriveSCoreChanged(float leftRightScore, float forwardScore, float backwardScore);
    }

    public DriveScoreManager(){

        leftRightScore = 0.0f;
        forwardScore = 0.0f;
        backwardScore = 0.0f;



        //fake update
        updateThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(updatePeriod*1000);
                        leftRightScore  = randomWithRange(1,100);
                        forwardScore  = randomWithRange(1,100);
                        backwardScore  = randomWithRange(1,100);
                        informDataChanged();
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        });


        updateThread.start(); // the while thread will start in BG thread

    }

    public static DriveScoreManager getInstance(){
        if(instance!=null){
            return instance;
        }
        else {
            instance = new DriveScoreManager();
            return instance;
        }
    }


    public void setDriveScoreChangeListener(DriveScoreManager.DriveScoreChangeListener listener){
        this.driveScoreChangeListener = listener;
    }

    private void informDataChanged(){
        if (driveScoreChangeListener != null) {
            driveScoreChangeListener.onDriveSCoreChanged(leftRightScore, forwardScore, backwardScore);
        }
    }

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public void setUpdatePeriod(int updatePeriod){
        this.updatePeriod = updatePeriod;
    }

    public int getUpdatePeriod(){
        return this.updatePeriod;
    }
}
