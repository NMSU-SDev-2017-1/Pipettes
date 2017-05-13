/*
 * Copyright (c) 2010, 2013 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package pipettes.ui.preview;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * A controller class to bind up a timeline to play controller buttons
 */
public class TimelineController
{
  private final Button startButton;
  private final Button rewindButton;
  private final ToggleButton playButton;
  private final Button fastForwardButton;
  private final Button endButton;
  private final ToggleButton loopButton;

  private final ChangeListener<Number> rateListener = new ChangeListener<Number>()
  {
    @Override
    public void changed(ObservableValue<? extends Number> observable,
        Number oldValue, Number newRate)
    {
      if (newRate.intValue() == 0 && playButton.isSelected())
      {
        playButton.setSelected(false);
      }
    }
  };

  private final SimpleObjectProperty<Timeline> timeline = new SimpleObjectProperty<Timeline>()
  {
    private Timeline old;

    @Override
    protected void invalidated()
    {
      Timeline t = get();
      if (old != null)
      {
        old.currentRateProperty().removeListener(rateListener);
      }
      if (t == null)
      {
        startButton.setDisable(true);
        rewindButton.setDisable(true);
        playButton.setDisable(true);
        fastForwardButton.setDisable(true);
        endButton.setDisable(true);
        loopButton.setDisable(true);
      }
      else
      {
        startButton.setDisable(false);
        rewindButton.setDisable(false);
        playButton.setDisable(false);
        fastForwardButton.setDisable(false);
        endButton.setDisable(false);
        loopButton.setDisable(false);
        playButton.setSelected(t.getCurrentRate() != 0);
        loopButton
            .setSelected(t.getCycleDuration().equals(Timeline.INDEFINITE));
        t.currentRateProperty().addListener(rateListener);
      }
      old = t;
    }
  };

  public Timeline getTimeline()
  {
    return timeline.get();
  }

  public SimpleObjectProperty<Timeline> timelineProperty()
  {
    return timeline;
  }

  public void setTimeline(Timeline timeline)
  {
    this.timeline.set(timeline);
  }

  public TimelineController(Button startBtn, Button rwBtn,
      final ToggleButton playBtn, Button ffBtn, Button endBtn,
      final ToggleButton loopBtn)
  {
    this.startButton = startBtn;
    this.rewindButton = rwBtn;
    this.playButton = playBtn;
    this.fastForwardButton = ffBtn;
    this.endButton = endBtn;
    this.loopButton = loopBtn;

    this.startButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        getTimeline().jumpTo(Duration.ZERO);
        getTimeline().pause();
      }
    });

    this.endButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        getTimeline().jumpTo(getTimeline().getTotalDuration());
        getTimeline().pause();
      }
    });

    this.playButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        System.out.println("playBtn.isSelected() = " + playBtn.isSelected());
        if (playBtn.isSelected())
        { // currently paused so play
          getTimeline().play();
        }
        else
        { // currently playing so pause
          getTimeline().pause();
        }
      }
    });

    this.fastForwardButton.setOnMousePressed(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        getTimeline().setRate(2);
      }
    });

    this.fastForwardButton.setOnMouseReleased(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        getTimeline().setRate(1);
      }
    });

    this.rewindButton.setOnMousePressed(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        getTimeline().setRate(-2);
      }
    });

    this.rewindButton.setOnMouseReleased(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        getTimeline().setRate(1);
      }
    });

    this.loopButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        if (loopBtn.isSelected())
        {
          getTimeline().stop();
          getTimeline().setCycleCount(Timeline.INDEFINITE);
          getTimeline().play();
        }
        else
        {
          getTimeline().stop();
          getTimeline().setCycleCount(1);
          getTimeline().play();
        }
      }
    });
  }
}
