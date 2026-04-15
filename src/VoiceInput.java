import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;

public class VoiceInput {

    public static String getCommand() {
        try {
            Model model = new Model("vosk-model-small-en-us-0.15");
            Recognizer recognizer = new Recognizer(model, 16000);

            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);

            TargetDataLine mic = AudioSystem.getTargetDataLine(format);
            mic.open(format);
            mic.start();

            byte[] buffer = new byte[4096];

            System.out.println("Speak now...");

            long startTime = System.currentTimeMillis();
            long silenceTimer = System.currentTimeMillis();

            while (true) {
                int bytesRead = mic.read(buffer, 0, buffer.length);

                byte[] converted = downsample(buffer, bytesRead);

                boolean isFinal = recognizer.acceptWaveForm(converted, converted.length);

                // If speech detected → reset silence timer
                if (bytesRead > 0) {
                    silenceTimer = System.currentTimeMillis();
                }

                // If final result comes → return it
                if (isFinal) {
                    String result = recognizer.getResult();

                    result = result.replaceAll("[^a-zA-Z0-9. ]", "").trim();

                    cleanup(mic, recognizer, model);
                    return result;
                }

                // If silence for 2 seconds → force final result
                if (System.currentTimeMillis() - silenceTimer > 2000) {
                    String result = recognizer.getFinalResult();

                    result = result.replaceAll("[^a-zA-Z0-9. ]", "").trim();

                    cleanup(mic, recognizer, model);
                    return result;
                }

                // Safety timeout (5 sec max)
                if (System.currentTimeMillis() - startTime > 5000) {
                    cleanup(mic, recognizer, model);
                    return "";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private static void cleanup(TargetDataLine mic, Recognizer recognizer, Model model) {
        mic.stop();
        mic.close();
        recognizer.close();
        model.close();
    }

    // Downsample 44100 → 16000
    public static byte[] downsample(byte[] input, int length) {
        int newLength = length / 3;
        byte[] output = new byte[newLength];

        for (int i = 0, j = 0; i < newLength && j < length; i++, j += 3) {
            output[i] = input[j];
        }

        return output;
    }
}