package com.haier.ai.bluetoothspeaker.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.haierubic.ai.ErrorCode;
import com.haierubic.ai.FilterData;
import com.haierubic.ai.IFilter;

public class AudioInput extends IFilter {

	private static final String Tag = AudioInput.class.getName();

	@Override
	public int output(FilterData arg0) {

		// Log.d(Tag, "output enter");
		if (mRecord == null || mReadBuf == null) {
			return ErrorCode.UAI_ERR_ASR_NOT_WORKING;
		}

		int n = mRecord.read(mReadBuf, 0, mMinBufLen);

		if (n > 0) {
			arg0.setData(mReadBuf, n);
			return ErrorCode.UAI_ERR_NONE;
		}

		return ErrorCode.UAI_ERR_ASR_FAILED;
	}

	@Override
	public int process(FilterData arg0) {
		return ErrorCode.UAI_ERR_NONE;
	}

	@Override
	public int start(String arg0) {
		
		try {
			if (mRecord != null && mRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
				Log.e(Tag, String.format("AudioRecord is recording already."));
				return ErrorCode.UAI_ERR_ASR_BUSY;
			}

			if (mRecord == null) {
				mMinBufLen = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT);

				if (AudioRecord.ERROR == mMinBufLen || AudioRecord.ERROR_BAD_VALUE == mMinBufLen) {
					Log.e(Tag, String.format("error: minBufLen(%d)", mMinBufLen));
					return ErrorCode.UAI_ERR_ASR_FAILED;
				}

				mReadBuf = new byte[mMinBufLen];
				mRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT, mMinBufLen);
			}

			
			mRecord.startRecording();
			Log.e(Tag, String.format("AudioRecord starts recording."));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ErrorCode.UAI_ERR_ASR_FAILED;
		}
		
		return ErrorCode.UAI_ERR_NONE;
	}

	@Override
	public int stop() {
		try {
			if (mRecord == null) {
				Log.e(Tag, String.format("AudioRecord is null."));
				return ErrorCode.UAI_ERR_NONE;
			}
			
			if (mRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
				Log.e(Tag, String.format("AudioRecord is not started."));
				return ErrorCode.UAI_ERR_NONE;
			}

			mRecord.stop();
			Log.d(Tag, String.format("AudioRecord is stopped."));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ErrorCode.UAI_ERR_NONE;
	}

	private AudioRecord mRecord;
	private int mMinBufLen;
	private byte mReadBuf[];
}
