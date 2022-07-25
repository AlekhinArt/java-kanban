package service.typeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import service.task.SubTask;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SubTaskAdapter extends TypeAdapter <SubTask> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Override
    public void write(JsonWriter jsonWriter, SubTask subTask) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(subTask.getName());
        jsonWriter.name("description");
        jsonWriter.value(subTask.getDescription());
        jsonWriter.name("id");
        jsonWriter.value(subTask.getId());
        jsonWriter.name("type");
        jsonWriter.value("SUBTASK");
        jsonWriter.name("epicId");
        jsonWriter.value(subTask.getEpicId());
        jsonWriter.name("status");
        jsonWriter.value(subTask.getStatus().toString());
        if (subTask.getStartTime()!= null){
            jsonWriter.name("startTime");
            jsonWriter.value(subTask.getStartTime().format(formatter));
            jsonWriter.name("duration");
            jsonWriter.value(subTask.getDuration());
        }
        jsonWriter.endObject();

    }

    @Override
    public SubTask read(JsonReader jsonReader) {
        return null;
    }
}
