package service.task;

public enum Type {
    EPIC("EPIC"),
    SUBTASK("SUBTASK"),
    TASK("TASK");

    public boolean compare (String value) {
        return toString().equals(value);
    }

    private final String typeName;

    Type (String typeName){
        this.typeName = typeName;
    }
    public String getTypeName(){
        return typeName;
    }
}