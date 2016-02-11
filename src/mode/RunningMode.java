/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mode;

import dataModel.InputDataModel;

/**
 *
 * @author elmarce
 */
public class RunningMode implements RunningModeInterface {

    private final InputDataModel IDM;

    public RunningMode(InputDataModel idm) {
        this.IDM = idm;
    }

    @Override
    public void mode1() {
        System.out.println("Mode 1");
    }

    @Override
    public void mode2() {
        System.out.println("Mode 2");
    }

    @Override
    public void mode3() {
        System.out.println("Mode 3");
        String mapperCommand = null;
    }

}
