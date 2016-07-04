/*
 * Copyright (c) 2004-2015 Universidade do Porto - Faculdade de Engenharia
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
 * Author: Manuel Ribeiro
 * Nov 23, 2015
 */
package pt.lsts.neptus.mp.maneuvers;

import pt.lsts.imc.IMCMessage;
import pt.lsts.neptus.mp.ManeuverLocation;
import pt.lsts.neptus.types.coord.LocationType;

/**
 * @author Manuel Ribeiro
 *
 */
public class Drop extends Goto {

    protected static final String DEFAULT_ROOT_ELEMENT = "Drop";

    @Override
    public String getType() {
        return "Drop";
    }

    @Override
    public Object clone() {  
        Drop clone = new Drop();
        super.clone(clone);
        clone.params = params;
        clone.setManeuverLocation(getManeuverLocation());
        clone.setRadiusTolerance(getRadiusTolerance());
        clone.setSpeedUnits(getUnits());
        clone.setSpeed(getSpeed());
        clone.setSpeedTolerance(getSpeedTolerance());
        
        return clone;
    }
    
    @Override
    public void parseIMCMessage(IMCMessage message) {
        try {
            pt.lsts.imc.Drop msg = pt.lsts.imc.Drop.clone(message);
            
            setMaxTime(msg.getTimeout());
            setSpeed(msg.getSpeed());
            switch (msg.getSpeedUnits()) {
                case METERS_PS:
                    setSpeedUnits("m/s");
                    break;
                case PERCENTAGE:
                    setSpeedUnits("%");
                    break;
                case RPM:
                    setSpeedUnits("RPM");
                    break;
            }
            ManeuverLocation pos = new ManeuverLocation();
            pos.setLatitudeRads(msg.getLat());
            pos.setLongitudeRads(msg.getLon());
            pos.setZ(msg.getZ());
            pos.setZUnits(ManeuverLocation.Z_UNITS.valueOf(msg.getZUnits().toString()));
            setManeuverLocation(pos);
            setCustomSettings(msg.getCustom());
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    
    public IMCMessage serializeToIMC() {
        pt.lsts.imc.Drop dropManeuver = new pt.lsts.imc.Drop();
        dropManeuver.setTimeout(this.getMaxTime());
        LocationType l = getManeuverLocation();
        l.convertToAbsoluteLatLonDepth();
        
        dropManeuver.setLat(l.getLatitudeRads());
        dropManeuver.setLon(l.getLongitudeRads());
        dropManeuver.setZ(getManeuverLocation().getZ());
        dropManeuver.setZUnits(pt.lsts.imc.Drop.Z_UNITS.valueOf(getManeuverLocation().getZUnits().name()));
        dropManeuver.setSpeed(this.getSpeed());
       
        switch (this.getUnits()) {
            case "m/s":
                dropManeuver.setSpeedUnits(pt.lsts.imc.Drop.SPEED_UNITS.METERS_PS);
                break;
            case "RPM":
                dropManeuver.setSpeedUnits(pt.lsts.imc.Drop.SPEED_UNITS.RPM);
                break;
            case "%":
                dropManeuver.setSpeedUnits(pt.lsts.imc.Drop.SPEED_UNITS.PERCENTAGE);
                break;
            default:
                dropManeuver.setSpeedUnits(pt.lsts.imc.Drop.SPEED_UNITS.RPM);
                break;
        }
        
        dropManeuver.setCustom(getCustomSettings());

        return dropManeuver;
    }   
}