package edu.ucsd.cse110.team13.bof.util;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeProfile;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.Optional;
import java.util.function.Consumer;

import edu.ucsd.cse110.team13.bof.model.IProfile;

public class NearbyWaveListener extends MessageListener {
    // this function is called to let UI update list when receiving new profile
    private final Consumer<String> onWaveReceivedFunc;

    public NearbyWaveListener(Consumer<String> onWaveReceivedFunc) {
        super();
        this.onWaveReceivedFunc = onWaveReceivedFunc;
    }

    @Override
    public void onFound(@NonNull Message message) {
        String student_id = new String(message.getContent());

        // let UI handles adding new student profile
        onWaveReceivedFunc.accept(student_id);
    }
}
