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

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_register', 'app_login');
	}
	
	public function app_register() {
		if(!empty($this->data)) {
			$response['status'] = 'error';
			$response['message'] = __('', true);
			
			$this->User->set($this->data);
			if($ok = $this->User->validates())
			{
				$this->request->data['User']['password'] = $this->Auth->password($this->data['User']['password_original']);
				if($ok = $this->User->save($this->data)) {
					$response['status'] = 'ok';
					$response['message'] = __('', true);
				}
			}

			if(!$ok) {
				$response['message'] = __('', true);
				$response['content'] = $this->User->invalidFields();
			}

			$this->set('response', $response);
		}
	}
	
	public function app_login() {
		if(!empty($this->data)) {
			$response['status'] = 'error';
			$response['message'] = __('', true);
			
			if($response['content'] = $this->User->find('first', array('conditions' => array('User.email' => $this->data['User']['email'], 'User.password' => $this->Auth->password($this->data['User']['password']))))) {
				$response['status'] = 'ok';
				$response['message'] = __('', true);
			}
			else {
				$response['message'] = __('Incorrect Email/Password', true);
			}
			
			$this->set('response', $response);
		}
	}
}
