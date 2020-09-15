package task;

public class Task {
    protected String description;
    protected boolean isDone;
    protected static int numberOfTasks = 0;

    public Task(String description) {
        this.description = description;
        isDone = false;
        numberOfTasks++;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDone(boolean isDone){
        this.isDone = isDone;
    }

    public boolean getDone() {
        return this.isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public int getNumberOfTasks(){
        return numberOfTasks;
    }

    @Override
    public String toString(){
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}