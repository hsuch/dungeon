package edu.virginia.engine.display;

import java.util.ArrayList;

public class Animation {

    private String id;

    private Integer startFrame;

    private Integer endFrame;

    private String fileName;

    public Animation(String id, Integer startFrame, Integer endFrame) {
        this.setId(id);
        this.setStartFrame(startFrame);
        this.setEndFrame(endFrame);
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Integer getStartFrame() {
        return this.startFrame;
    }
    public void setStartFrame(Integer startFrame) {
        this.startFrame = startFrame;
    }

    public Integer getEndFrame() {
        return this.endFrame;
    }
    public void setEndFrame(Integer endFrame) {
        this.endFrame = endFrame;
    }


}
