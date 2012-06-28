<?php

App::uses('AppController', 'Controller');

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
				$this->request->data['User']['password'] = $this->Auth->password($this->request->data['User']['password']);
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
