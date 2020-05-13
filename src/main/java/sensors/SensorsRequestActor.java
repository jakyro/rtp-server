package sensors;

import actors.MyActor;
import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;
import model.Packet;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public class SensorsRequestActor extends MyActor {

    public static void main(String[] args) {
        new SensorsRequestActor(args[0], Long.parseLong(args[1]));
    }

    SensorsRequestActor(String name, long pid) {
        super(name, pid);

        Request request = new Request.Builder().url("http://localhost:4000/sensors").build();
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.SECONDS).build();
        OkSse okSse = new OkSse(client);
        ServerSentEvent sse = okSse.newServerSentEvent(request, new RequestListener(this));
    }

    @Override
    public void parseMessage(Packet packet) {
    }

    private static class RequestListener implements ServerSentEvent.Listener {

        private final SensorsRequestActor parent;

        public RequestListener(SensorsRequestActor parent) {
            this.parent = parent;
        }

        @Override
        public void onOpen(ServerSentEvent sse, Response response) {

        }

        @Override
        public void onMessage(ServerSentEvent sse, String id, String event, String message) {
            parent.sendMessage(SensorsParserActor.host_name, message);
        }

        @Override
        public void onComment(ServerSentEvent sse, String comment) {

        }

        @Override
        public boolean onRetryTime(ServerSentEvent sse, long milliseconds) {
            return true;
        }

        @Override
        public boolean onRetryError(ServerSentEvent sse, Throwable throwable, Response response) {
            return true;
        }

        @Override
        public void onClosed(ServerSentEvent sse) {
            parent.sendMessage("supervisor", "SSE CLOSED");
        }

        @Override
        public Request onPreRetry(ServerSentEvent sse, Request originalRequest) {
            return null;
        }
    }
}
