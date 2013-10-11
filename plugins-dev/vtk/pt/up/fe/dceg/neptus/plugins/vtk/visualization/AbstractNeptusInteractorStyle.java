/*
 * Copyright (c) 2004-2013 Universidade do Porto - Faculdade de Engenharia
 * Laboratório de Sistemas e Tecnologia Subaquática (LSTS)
 * All rights reserved.
 * Rua Dr. Roberto Frias s/n, sala I203, 4200-465 Porto, Portugal
 *
 * This file is part of Neptus, Command and Control Framework.
 *
 * Commercial Licence Usage
 * Licencees holding valid commercial Neptus licences may use this file
 * in accordance with the commercial licence agreement provided with the
 * Software or, alternatively, in accordance with the terms contained in a
 * written agreement between you and Universidade do Porto. For licensing
 * terms, conditions, and further information contact lsts@fe.up.pt.
 *
 * European Union Public Licence - EUPL v.1.1 Usage
 * Alternatively, this file may be used under the terms of the EUPL,
 * Version 1.1 only (the "Licence"), appearing in the file LICENSE.md
 * included in the packaging of this file. You may not use this work
 * except in compliance with the Licence. Unless required by applicable
 * law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the Licence for the specific
 * language governing permissions and limitations at
 * https://www.lsts.pt/neptus/licence.
 *
 * For more information please see <http://lsts.fe.up.pt/neptus>.
 *
 * Author: hfq
 * Oct 8, 2013
 */
package pt.up.fe.dceg.neptus.plugins.vtk.visualization;

import java.util.LinkedHashMap;

import pt.up.fe.dceg.neptus.plugins.vtk.pointcloud.PointCloud;
import pt.up.fe.dceg.neptus.plugins.vtk.pointtypes.PointXYZ;
import vtk.vtkInteractorStyleTrackballCamera;
import vtk.vtkLegendScaleActor;
import vtk.vtkPNGWriter;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkRenderer;
import vtk.vtkScalarBarActor;
import vtk.vtkTextActor;
import vtk.vtkWindowToImageFilter;

/**
 * @author hfq
 * 
 */
public abstract class AbstractNeptusInteractorStyle extends vtkInteractorStyleTrackballCamera {

    // public vtkCanvas canvas;
    public Canvas canvas;
    // A renderer
    public vtkRenderer renderer;
    // The render Window Interactor
    public vtkRenderWindowInteractor interactor;
    
    public LinkedHashMap<String, PointCloud<PointXYZ>> linkedHashMapCloud;
    
    // Text Actor for fps
    protected vtkTextActor fpsActor = new vtkTextActor();
    // frame per seconds text actor - show frame rate refresh on visualizer
    private boolean fpsActorEnable = false;

    // A PNG Writer for screenshot captures
    protected vtkPNGWriter snapshotWriter = new vtkPNGWriter();
    // Internal Window to image Filter. Needed by a snapshotWriter object
    protected vtkWindowToImageFilter wif = new vtkWindowToImageFilter();

    // Actor for 2D grid on screen
    protected vtkLegendScaleActor gridActor = new vtkLegendScaleActor();
    // Set true if the grid actor is enabled
    private boolean gridEnabled = false;


    // Actor for 2D loookup table on screen
    protected vtkScalarBarActor lutActor = new vtkScalarBarActor();
    protected ScalarBar scalarBar;
    // Set true if the LUT actor is enabled
    private boolean lutEnabled = true;
    

    /**
     *
     */
    public AbstractNeptusInteractorStyle() {
        super();
    }

    /**
     * @param canvas
     * @param renderer
     * @param interact
     */
    public AbstractNeptusInteractorStyle(Canvas canvas, vtkRenderer renderer, vtkRenderWindowInteractor interactor) {
        super();
        this.canvas = canvas;
        this.renderer = renderer;
        this.interactor = interactor;
    }

    /**
     * @param canvas
     * @param renderer
     * @param interact
     * @param linkedHashMapCloud
     */
    public AbstractNeptusInteractorStyle(Canvas canvas, vtkRenderer renderer, vtkRenderWindowInteractor interactor,
            LinkedHashMap<String, PointCloud<PointXYZ>> linkedHashMapCloud) {
        super();
        this.canvas = canvas;
        this.renderer = renderer;
        this.interactor = interactor;
        this.linkedHashMapCloud = linkedHashMapCloud;
    }

    /**
     * Simple initializer for the interactor style switches on the times and the observers handlers adds and observer to
     * the callbackFunctionFPS
     */
    protected void simpleInitialize() {
        UseTimersOn();
        HandleObserversOn();

        getInteractor().AddObserver("RenderEvent", this, "callbackFunctionFPS");
    }

    protected abstract void Initialize();

    protected void callbackFunctionFPS() {
        double timeInSeconds = this.renderer.GetLastRenderTimeInSeconds();
        double fps = 1.0 / timeInSeconds;

        fps = Math.round(fps * 100) / 100.0d;
        fpsActor.SetInput(String.valueOf(fps));

        fpsActor.GetTextProperty().SetColor(0.0, 1.0, 0.0);
        fpsActor.UseBorderAlignOn();
        fpsActor.SetDisplayPosition(2, 2);

        if (fpsActorEnable == false) {
            fpsActorEnable = true;
            this.renderer.AddActor(fpsActor);
        }
    }

    protected abstract void callbackPointCoords();

    /**
     * @return the interactor
     */
    protected vtkRenderWindowInteractor getInteractor() {
        return interactor;
    }

    /**
     * @param interactor the interactor to set
     */
    protected void setInteractor(vtkRenderWindowInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * @return the lutActor
     */
    public vtkScalarBarActor getLutActor() {
        return lutActor;
    }

    /**
     * @param lutActor the lutActor to set
     */
    public void setLutActor(vtkScalarBarActor lutActor) {
        this.lutActor = lutActor;
    }

    /**
     * @return the scalarBar
     */
    public ScalarBar getScalarBar() {
        return scalarBar;
    }

    /**
     * @param scalarBar the scalarBar to set
     */
    public void setScalarBar(ScalarBar scalarBar) {
        this.scalarBar = scalarBar;
    }
}
