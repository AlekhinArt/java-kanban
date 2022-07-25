package service.typeAdapter;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import service.task.Task;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TaskAdapter extends TypeAdapter<Task> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    @Override
    public void write(JsonWriter jsonWriter, Task task) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(task.getName());
        jsonWriter.name("description");
        jsonWriter.value(task.getDescription());
        jsonWriter.name("id");
        jsonWriter.value(task.getId());
        jsonWriter.name("type");
        jsonWriter.value("TASK");
        jsonWriter.name("status");
        jsonWriter.value(task.getStatus().toString());
        if (task.getStartTime() != null) {
            jsonWriter.name("startTime");
            jsonWriter.value(task.getStartTime().format(formatter));
            jsonWriter.name("duration");
            jsonWriter.value(task.getDuration());
        }
        jsonWriter.endObject();

    }

    @Override
    public Task read(JsonReader jsonReader) {
        return null;
    }
}
