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
 * Author: tsmarques
 * 23 Mar 2015
 */
package pt.lsts.neptus.plugins.preflight.section;

import java.awt.Color;

import com.google.common.eventbus.Subscribe;

import pt.lsts.imc.EstimatedState;
import pt.lsts.neptus.plugins.preflight.PreflightSection;
import pt.lsts.neptus.plugins.preflight.check.ManualCheck;
import pt.lsts.neptus.plugins.preflight.check.TestCheck;
import pt.lsts.neptus.plugins.preflight.check.automated.CheckLostComms;
import pt.lsts.neptus.plugins.preflight.check.automated.DiskSpaceCheck;

/**
 * @author tsmarques
 *
 */
@SuppressWarnings("serial")
public class AnotherTestSection extends PreflightSection {
    public AnotherTestSection(String t) {
        super("Another test section" + t);
        setBackground(Color.WHITE);
    }

    @Override
    protected void buildChecksPanel() { 
        addNewCheckItem(new ManualCheck("Manual Check", "Status", false));
        addNewCheckItem(new CheckLostComms(false));
        addNewCheckItem(new DiskSpaceCheck(false));
    }
}
