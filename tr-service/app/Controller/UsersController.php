<?php
/**
 * Static content controller.
 *
 * This file will render views from views/pages/
 *
 * PHP 5
 *
 * CakePHP(tm) : Rapid Development Framework (http://cakephp.org)
 * Copyright 2005-2012, Cake Software Foundation, Inc. (http://cakefoundation.org)
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright     Copyright 2005-2012, Cake Software Foundation, Inc. (http://cakefoundation.org)
 * @link          http://cakephp.org CakePHP(tm) Project
 * @package       app.Controller
 * @since         CakePHP(tm) v 0.2.9
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 */

App::uses('AppController', 'Controller');

/**
 * Static content controller
 *
 * Override this controller by placing a copy in controllers directory of an application
 *
 * @package       app.Controller
 * @link http://book.cakephp.org/2.0/en/controllers/pages-controller.html
 */
class UsersController extends AppController {

	public $name = 'Users';
	public $helpers = array('Html', 'Session');

	public function beforeFilter() {
		parent::beforeFilter();
	}
	
	public function app_register() {
		
		if(!empty($this->data)) {
			if($this->User->save($this->data)) {
				
			}
		}
	}
	
	public function app_login() {
		
		$response['message'] = '';
		$response['status'] = '';
		
		if(!empty($this->data)) {
			if($response['content'] = $this->User->find('first', array('conditions' => array('User.email' => $email, 'User.password' => md5($password))))) {
				$response['message'] = '';
				$response['status'] = '';
			}
			else {
				$response['message'] = '';
			}
		}
	}
}
