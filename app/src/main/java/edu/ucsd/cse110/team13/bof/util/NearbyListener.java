package edu.ucsd.cse110.team13.bof.util;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeMessage;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;

public class NearbyListener extends MessageListener {
    // this function is called to let UI update list when receiving new profile
    private final Consumer<ProfileWithCourses>    onProfileReceivedFunc;
    private final BiConsumer<String,List<String>> onWaveUidsReceivedFuc;

    public NearbyListener(
            Consumer<ProfileWithCourses>    onProfileReceivedFunc,
            BiConsumer<String,List<String>> onWaveUidsReceivedFuc) {
        super();
        this.onProfileReceivedFunc = onProfileReceivedFunc;
        this.onWaveUidsReceivedFuc = onWaveUidsReceivedFuc;
        Log.i("BOF_Nearby_Listener", "Created");
    }

    @Override
    public void onFound(@NonNull Message message) {
        String messageEncoded = new String(message.getContent());
        Log.i("BOF_Nearby_Listener", "Found: " + messageEncoded);
        Optional<Pair<ProfileWithCourses, List<String>>> messageOp = decodeMessage(messageEncoded);

        if(messageOp.isPresent()) {
            // 1. handles adding/updating received profile
            onProfileReceivedFunc.accept(messageOp.get().first);
            // 2. handles incoming wave uid list
            onWaveUidsReceivedFuc.accept(messageOp.get().first.getUid(), messageOp.get().second);
        }
    }
}
