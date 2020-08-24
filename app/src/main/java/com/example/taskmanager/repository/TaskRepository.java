package com.example.taskmanager.repository;


import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TaskRepository  {
    private List<Task> mTasksTodo;
    private List<Task> mTasksDoing;
    private List<Task> mTasksDone;
    private static TaskRepository mTaskRepository;

    private TaskRepository() {
        mTasksTodo = new ArrayList<>();
        mTasksDoing = new ArrayList<>();
        mTasksDone = new ArrayList<>();
    }

    public static TaskRepository getInstance() {
        if (mTaskRepository == null) {
            mTaskRepository = new TaskRepository();
        }
        return mTaskRepository;
    }


    public void setList(List<Task> list) {
        if(list.size()>0){
            List<Task> taskList = getList(list.get(0).getTaskState());
            taskList = list;
        }
    }


    public List<Task> getList(State state) {
        switch (state){
            case TODO: return mTasksTodo;
            case DOING: return mTasksDoing;
            case DONE: return mTasksDone;
        }
        return null;
    }


    public void insert(Task task) {
        List<Task> taskList = getList(task.getTaskState());
        taskList.add(task);
    }


    public void remove(Task task) {
        UUID id = task.getUUID();
        Task task1 = get(id,task.getTaskState());
        List<Task> taskList = getList(task1.getTaskState());
        taskList.remove(task1);
    }


    public void remove(int index, State state) {
        List<Task> taskList = getList(state);
        taskList.remove(index);
    }


    public Task get(UUID uuid) {

        for (Task task : mTasksTodo) {
            if (task.getUUID().equals(uuid)) {
                return task;
            }
        }
        for (Task task : mTasksDoing) {
            if (task.getUUID().equals(uuid)) {
                return task;
            }
        }
        for (Task task : mTasksDone) {
            if (task.getUUID().equals(uuid)) {
                return task;
            }
        }
        return null;
    }

    public Task get(UUID uuid, State state) {
        List<Task> taskList = getList(state);
        for (Task task : taskList) {
            if (task.getUUID().equals(uuid)) {
                return task;
            }
        }
        return null;
    }

    public void createRandomTaskList(int listSize, String title) {
        for (int i = 0; i < listSize; i++) {
            State randomState = getRandomState();
            Task task = new Task(title, randomState);
            getList(randomState).add(task);
        }
    }


    public static State getRandomState() {
        State state = State.TODO;
        Random rand = new Random();
        int randomNumber = rand.nextInt(3);
        switch (randomNumber) {
            case 0:
                state = State.TODO;
                break;
            case 1:
                state = State.DOING;
                break;
            case 2:
                state = State.DONE;
                break;
            default:
                break;
        }
        return state;
    }

    public void update(Task task){
        UUID id = task.getUUID();
        Task task1 = get(id,task.getTaskState());
        if(task1!=null){
            task1.setTaskTitle(task.getTaskTitle());
            task1.setTaskDescription(task.getTaskDescription());
            task1.setDate(task1.getDate());
            task1.setTaskState(task1.getTaskState());
        }else {
            //when change state we should insert task to its list
            insert(task);
            //when change state we should remove task from primary list
            for(State state : State.values()){
                if(state != task.getTaskState()){
                    Task task2 = get(task.getUUID(),state);
                    if(task2!=null){
                        remove(task2);
                    }
                }
            }
        }
    }


}
