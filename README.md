# Find current latitude and longitude and also there address and city and state 
able to find all these details while your mobile lock or unlock.
Using JobServices Class

This is the base class that handles asynchronous requests that were previously scheduled. You are responsible for overriding onStartJob(JobParameters), which is where you will implement your job logic.

This service executes each incoming job on a Handler running on your application's main thread. This means that you must offload your execution logic to another thread/handler/AsyncTask of your choosing. Not doing so will result in blocking any future callbacks from the JobManager - specifically onStopJob(android.app.job.JobParameters), which is meant to inform you that the scheduling requirements are no longer being met.
