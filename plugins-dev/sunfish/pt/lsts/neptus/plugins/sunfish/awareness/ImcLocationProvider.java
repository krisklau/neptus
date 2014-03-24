/*
 * Copyright (c) 2004-2014 Universidade do Porto - Faculdade de Engenharia
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
 * Author: zp
 * Mar 24, 2014
 */
package pt.lsts.neptus.plugins.sunfish.awareness;

import com.google.common.eventbus.Subscribe;

import pt.lsts.imc.Announce;
import pt.lsts.imc.RemoteSensorInfo;
import pt.lsts.neptus.comm.manager.imc.ImcMsgManager;

/**
 * @author zp
 * 
 */
public class ImcLocationProvider implements ILocationProvider {

    private SituationAwareness instance;

    @Override
    public void onInit(SituationAwareness instance) {
        this.instance = instance;
        ImcMsgManager.registerBusListener(this);
    }

    @Override
    public void onCleanup() {
        ImcMsgManager.unregisterBusListener(this);
    }

    @Subscribe
    public void on(Announce announce) {
        AssetPosition pos = new AssetPosition(announce.getSysName(), Math.toDegrees(announce.getLat()),
                Math.toDegrees(announce.getLon()));
        pos.setSource("Announce");
        switch (announce.getSysType()) {
            case STATICSENSOR:
            case MOBILESENSOR:
            case HUMANSENSOR:
                pos.setType("Sensor");
                break;
            default:
                pos.setType(announce.getSysType().toString());
                break;
        }
        
        instance.addAssetPosition(pos);
    }
    
    @Subscribe
    public void on(RemoteSensorInfo sensor) {
        AssetPosition pos = new AssetPosition(sensor.getId(), Math.toDegrees(sensor.getLat()),
                Math.toDegrees(sensor.getLon()));
        pos.setTimestamp(sensor.getTimestampMillis());
        pos.setSource("RemoteSensorInfo");
        pos.setType(sensor.getSensorClass());
        instance.addAssetPosition(pos);
    }
}
