/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

//	// Load teacher navigation
//	loadTeacherNavigation();
//
//	// Load the home form first
//	loadForm("Home");
//	
//	//Bind buttons accordingly
//	bindButtons();
//	
//	// We need to catch what happens when a user presses
//	// a navigation tab
//	bindClickToTeacherNavigation();
//
//	// Catch what happens when a menu item from the "+ Add Page"
//	// button gets pressed
//	bindClickToAddPageNavigation();
});


$('#menu-options').click(function() {
    $(this).parent().addClass('menu-item-divided pure-menu-selected').siblings().removeClass('menu-item-divided pure-menu-selected');
//    return false;
});
