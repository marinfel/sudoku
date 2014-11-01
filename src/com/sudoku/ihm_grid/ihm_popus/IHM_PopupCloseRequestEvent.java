/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.ihm_grid.ihm_popus;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @class IHM_PopupCloseRequestEvent is an event fired when a popup request to be closed
 * 
 * @author Marc-Antoine
 */
public class IHM_PopupCloseRequestEvent extends Event {
    
    //POPUP_CLOSEREQUEST a public constant to help to recognize this kind of event
    public static final EventType<IHM_PopupCloseRequestEvent> POPUP_CLOSEREQUEST = new EventType("POPUP_CLOSEREQUEST");
    
    //An attribut to access to the pop-up which fired the event 
    public final IHM_Popup popup;
    
    /**
     * IHM_PopupCloseRequestEvent constructor
     *
     * @param popup is the popup which fired the event
     */
    public IHM_PopupCloseRequestEvent(IHM_Popup popup) {
        super(POPUP_CLOSEREQUEST);
        
        //set the popup holds by the event
        this.popup = popup;
    }
}
