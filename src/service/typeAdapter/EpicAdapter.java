package service.typeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import service.task.Epic;
import service.task.Task;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class EpicAdapter extends TypeAdapter<Epic> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, Epic epic) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(epic.getName());
        jsonWriter.name("description");
        jsonWriter.value(epic.getDescription());
        jsonWriter.name("id");
        jsonWriter.value(epic.getId());
        jsonWriter.name("type");
        jsonWriter.value("EPIC");
        jsonWriter.name("status");
        jsonWriter.value(epic.getStatus().toString());
        jsonWriter.name("subTasksId");
        jsonWriter.beginArray();
        if (epic.getSubsId().size() > 0) {
            for (Integer id : epic.getSubsId()) {
                jsonWriter.value(id);
            }
        }
        jsonWriter.endArray();
        jsonWriter.name("subTasks");
        jsonWriter.beginArray();
        if (epic.getSubTasks().size() > 0) {
            for (Task task : epic.getSubTasks()) {
                jsonWriter.value(String.valueOf(task));
            }
        }
        jsonWriter.endArray();
        if (epic.getEndTime() != null) {
            jsonWriter.name("startTime");
            jsonWriter.value(epic.getStartTime().format(formatter));
            jsonWriter.name("duration");
            jsonWriter.value(epic.getDuration());
            jsonWriter.name("endTime");
            jsonWriter.value(epic.getEndTime().format(formatter));
        }
        jsonWriter.endObject();
    }

    @Override
    public Epic read(JsonReader jsonReader) {
        return null;
    }
}
