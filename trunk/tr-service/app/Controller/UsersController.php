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
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$this->User->set($this->data);
			if($ok = ($invalidFields = $this->User->invalidFields()) ? false : true)
			{
				$this->request->data['User']['password'] = $this->Auth->password($this->request->data['User']['password']);
				if($ok = $this->User->save($this->data)) {
					$response['result']['status'] = 'ok';
				}
			}

			if(!$ok) {
				$response['result']['status'] = 'error';
				$response['result']['invalidFields'] = $invalidFields;
			}

			$this->set('response', $response);
		}
	}
	
	public function app_login() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($user = $this->User->find('first', array('conditions' => array('User.email' => $this->data['User']['email'], 'User.password' => $this->Auth->password($this->data['User']['password']))))) {
				$response['result']['User'] = $user['User'];
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
	}
}
