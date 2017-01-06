package com.haier.ai.bluetoothspeaker.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.haierubic.ai.ErrorCode;
import com.haierubic.ai.FilterData;
import com.haierubic.ai.IFilter;

public class AudioOutput extends IFilter {

	private static final String Tag = AudioInput.class.getName();
	private AudioTrack mAudioTrack;
	
	@Override
	public int output(FilterData arg0) {
		Log.d(Tag, "output enter");
		
		return ErrorCode.UAI_ERR_NONE;
	}

	@Override
	public int process(FilterData arg0) {
		Log.d(Tag, "process enter");
		if (mAudioTrack == null)
		{
			return ErrorCode.UAI_ERR_TTS_NOT_WORKING;
		}
		
		int length = arg0.getDataLength();
		byte data[] = new byte[length];
		arg0.getData(data);
		
		int pos = 0;
		int len = length;
		int outLen;
		while ((outLen = mAudioTrack.write(data, pos, len)) > 0)
		{
			pos += outLen;
			len = length - pos;
		}
		
		return ErrorCode.UAI_ERR_NONE;
	}

	@Override
	public int start(String arg0) {
		Log.d(Tag, "start enter");
		
		try {
			if (mAudioTrack == null)
			{
				int minSize = AudioTrack.getMinBufferSize(16000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
				mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO, // 声道
						AudioFormat.ENCODING_PCM_16BIT, minSize * 4, AudioTrack.MODE_STREAM);
			}
			
			if (mAudioTrack != null && mAudioTrack.getPlaybackRate() != AudioTrack.PLAYSTATE_PLAYING)
			{
				mAudioTrack.play();
				return ErrorCode.UAI_ERR_NONE;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ErrorCode.UAI_ERR_ASR_FAILED;
		}
		
		
		return ErrorCode.UAI_ERR_TTS_NOT_WORKING;
	}

	@Override
	public int stop() {
		Log.d(Tag, "stop enter");
		try {
			if (mAudioTrack != null && mAudioTrack.getPlaybackRate() == AudioTrack.PLAYSTATE_PLAYING)
			{
				mAudioTrack.stop();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return ErrorCode.UAI_ERR_NONE;
	}

}
