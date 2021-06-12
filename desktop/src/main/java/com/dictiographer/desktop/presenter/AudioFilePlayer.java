package com.dictiographer.desktop.presenter;

import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class AudioFilePlayer {
    private SourceDataLine line;

    public static void main(String[] args) {
        try {
            final AudioFilePlayer player = new AudioFilePlayer();
            String file = "C:\\tmp\\test.mp3";
            byte[] bbs = IOUtils.toByteArray(new FileInputStream(file));
            player.play(bbs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void play(byte[] bytes) throws Exception {
//        final File file = new File(filePath);

        final AudioInputStream in = getAudioInputStream(new ByteArrayInputStream(bytes));

        final AudioFormat outFormat = getOutFormat(in.getFormat());
        final Info info = new Info(SourceDataLine.class, outFormat);

        line =
                (SourceDataLine) AudioSystem.getLine(info);

        if (line != null) {
            line.open(outFormat);
            line.start();
            stream(getAudioInputStream(outFormat, in), line);
            line.drain();
            line.stop();
        }
    }

    public void stop() {
        line.stop();
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}

