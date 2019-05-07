import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;

public class Messenger extends Observable { 
    private Socket socket;
    private String username;
    private Emitter.Listener messageListener;

    public Messenger(String username, Observer observer) {
      this.username = username;
      this.addObserver(observer);

      setUpSocket();
      setUpChannels();
    }

    public void endSession() {
      socket.disconnect();
      socket.close();
    }

    public void sendMessage( message ) {
      socket.emit("messagedetection", message, username);

    }

    private boolean setUpSocket() {
        try {
            socket = IO.socket("http://34.226.139.149/").connect();
            socket.emit("join", username);
            socket.on("message", messageListener);

            return true;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
            return false;
        }
    }

    private setUpChannels() {
        messageListener = args -> {
            JSONObject data = (JSONObject) args[0];

            runOnUiThread( () -> {
              try {
                  this.notifyObservers(this, MessageFactory.create(data))
              } catch (JSONException e) {
                  e.printStackTrace();
              }

            });
        };
    }
}