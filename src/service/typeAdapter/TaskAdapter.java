package service.typeAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import service.task.Task;
import service.task.Type;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskAdapter extends TypeAdapter<Task> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    Gson gson = new Gson();

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
    public Task read(JsonReader jsonReader) throws IOException {
      /*  final Task task = new Task();
        jsonReader.beginObject();
//        jsonReader.beginObject();
//        jsonReader.peek();
//        jsonReader.nextString()
//        String field;
//        JsonToken token = jsonReader.peek();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "name":
                    task.setName(jsonReader.nextString());
                    break;
                case "description":
                    task.setDescription(jsonReader.nextString());
                    break;
                case "id":
                    task.setId(jsonReader.nextInt());
                    break;
                case "type":
                    task.setType(Type.valueOf(jsonReader.nextString()));
                    break;


            }
        }

        jsonReader.endObject();*/
        return null;
    }
}
