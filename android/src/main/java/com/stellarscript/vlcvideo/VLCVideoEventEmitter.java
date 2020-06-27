package com.stellarscript.vlcvideo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.videolan.libvlc.MediaPlayer.TrackDescription;

final class VLCVideoEventEmitter {

    private final VLCVideoView mVideoView;
    private final RCTEventEmitter mEventEmitter;

    VLCVideoEventEmitter(final VLCVideoView videoView, final ThemedReactContext themedReactContext) {
        mVideoView = videoView;
        mEventEmitter = themedReactContext.getJSModule(RCTEventEmitter.class);
    }

    void emitOnBuffering(final double buffering) {
        final WritableMap event = Arguments.createMap();
        event.putDouble(VLCVideoEvents.ON_BUFFERING_BUFFERING_PROP, buffering);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_BUFFERING_EVENT, event);
    }

    void emitOnPlaying(final double duration) {
        final WritableMap event = Arguments.createMap();
        event.putDouble(VLCVideoEvents.ON_PLAYING_DURATION_PROP, duration);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_PLAYING_EVENT, event);
    }

    void emitOnPaused() {
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_PAUSED_EVENT, null);
    }

    void emitOnEndReached() {
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_END_REACHED_EVENT, null);
    }

    void emitOnError(final String message, final boolean isCritical) {
        final WritableMap event = Arguments.createMap();
        event.putString(VLCVideoEvents.ON_ERROR_MESSAGE_PROP, message);
        event.putBoolean(VLCVideoEvents.ON_ERROR_IS_CRITICAL_PROP, isCritical);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_ERROR_EVENT, event);
    }

    void emitOnTimeChanged(final double time) {
        final WritableMap event = Arguments.createMap();
        event.putDouble(VLCVideoEvents.ON_TIME_CHANGED_TIME_PROP, time);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_TIME_CHANGED_EVENT, event);
    }

    void emitOnSeekRequested(final double time) {
        final WritableMap event = Arguments.createMap();
        event.putDouble(VLCVideoEvents.ON_SEEK_REQUESTED_TIME_PROP, time);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_SEEK_REQUESTED_EVENT, event);
    }

    void emitOnSeekPerformed() {
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_SEEK_PERFORMED_EVENT, null);
    }

    void emitOnSubtitleTracksChanged(final TrackDescription[] tracks) {
        final WritableMap eventTracks = Arguments.createMap();
        if (tracks != null) {
            for (final TrackDescription track : tracks) {
                if (track.name.toLowerCase().contains("disable")) {
                    continue;
                }

                final WritableArray ids = eventTracks.hasKey(track.name) ?
                        Arguments.fromList(eventTracks.getArray(track.name).toArrayList())
                        :
                        Arguments.createArray();
                ids.pushInt(track.id);
                eventTracks.putArray(track.name, ids);
            }
        }

        final WritableMap event = Arguments.createMap();
        event.putMap(VLCVideoEvents.ON_SUBTITLE_TRACKS_CHANGED_SUBTITLE_TRACKS_PROP, eventTracks);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_SUBTITLE_TRACKS_CHANGED_EVENT, event);
    }

    void emitOnAudioTracksChanged(final TrackDescription[] tracks) {
        final WritableMap eventTracks = Arguments.createMap();
        if (tracks != null) {
            for (final TrackDescription track : tracks) {
                if (track.name.toLowerCase().contains("disable")) {
                    continue;
                }

                final WritableArray ids = eventTracks.hasKey(track.name) ?
                        Arguments.fromList(eventTracks.getArray(track.name).toArrayList())
                        :
                        Arguments.createArray();
                ids.pushInt(track.id);
                eventTracks.putArray(track.name, ids);
            }
        }

        final WritableMap event = Arguments.createMap();
        event.putMap(VLCVideoEvents.ON_AUDIO_TRACKS_CHANGED_AUDIO_TRACKS_PROP, eventTracks);
        mEventEmitter.receiveEvent(mVideoView.getId(), VLCVideoEvents.ON_AUDIO_TRACKS_CHANGED_EVENT, event);
    }
}
