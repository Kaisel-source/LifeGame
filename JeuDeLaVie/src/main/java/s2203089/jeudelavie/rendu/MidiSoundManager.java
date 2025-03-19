package s2203089.jeudelavie.rendu;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MidiSoundManager {

    private static MidiSoundManager instance;
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private int volume = 80;

    private MidiSoundManager() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channel = synthesizer.getChannels()[0]; // Utilisation du premier canal MIDI
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static MidiSoundManager getInstance() {
        if (instance == null) {
            instance = new MidiSoundManager();
        }
        return instance;
    }

    public void playNote(String color) {
        if (volume != 0) {
            int note = noteForColor(color);
            if (channel != null) {
                channel.noteOn(note, volume); // Vélocité = 80 (volume)
                new Thread(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ignored) {
                    }
                    channel.noteOff(note);
                }).start();
            }
        }
    }

    public void setVolume(int volume) {
        this.volume = volume;
        channel.controlChange(7, volume); // Volume MIDI
    }

    public int getVolume() {
        return volume;
    }

    private int noteForColor(String color) {
        int result;
        result = switch (color) {
            case "BLACK" ->
                1;
            case "ORANGE" ->
                2;
            case "WHITE" ->
                3;
            case "GREEN" ->
                4;
            case "MAGENTA" ->
                5;
            case "BLUE" ->
                6;
            case "CYAN" ->
                7;
            case "LIGHT_GRAY" ->
                8;
            case "RED" ->
                9;
            case "YELLOW" ->
                10;
            case "DARK_GRAY" ->
                11;
            default ->
                0; // Aucune note
        };
        return result;

    }
}
