/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.Rectangle;

/**
 *
 * @author SriHarsha S
 */
public interface LineNumberModel {

	public int getNumberLines();

	public Rectangle getLineRect(int line);
}
