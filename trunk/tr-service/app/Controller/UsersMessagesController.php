<?php
App::uses('AppController', 'Controller');

class UsersMessagesController extends AppController {

	public $name = 'UsersMessages';

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_getMessages', 'app_addMessage');
	}
	
	public function app_getMessages() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$response['result']['messages'] = $this->UsersMessage->find('all', array('conditions' => array('OR' => array(
																						array('UsersMessage.user_from_id' => $this->data['UsersMessage']['user1_id'], 'UsersMessage.user_to_id' => $this->data['UsersMessage']['user2_id']),
																						array('UsersMessage.user_from_id' => $this->data['UsersMessage']['user2_id'], 'UsersMessage.user_to_id' => $this->data['UsersMessage']['user1_id'])))));
			if($response['result']['messages']) {
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_addMessage() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($usersMessage = $this->UsersMessage->save($this->data)) {
				$response['result']['UsersMessage'] = $usersMessage['UsersMessage'];
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
