<?php
App::uses('AppController', 'Controller');

class UsersMessagesController extends AppController {

	public $name = 'UsersMessages';
	
	public $components = array('GCMNotification');

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
				$this->loadModel('User');
				$userTo = $this->User->find('first', array('fields' => array('User.android_device_id'), 'conditions' => array('User.id' => $this->data['UsersMessage']['user_to_id'])));
				if(!empty($userTo['User']['android_device_id'])) {
					$userFrom = $this->User->find('first', array('fields' => array('User.id', 'User.name', 'User.surname', 'User.username'), 'conditions' => array('User.id' => $this->data['UsersMessage']['user_from_id'])));
					$messageNotification = str_replace('@@@USER@@@', $userFrom['User']['name'].' '.$userFrom['User']['surname'], __('@@@USER@@@ le ha enviado un mensaje', true));
					$usersMessage['UserFrom'] = $userFrom['User'];
					$response['result']['notification'] = $this->GCMNotification->send($userTo['User']['android_device_id'], NOTIFICATION_MSG_SENT, $messageNotification, $this->data['UsersMessage']['user_to_id'], $usersMessage);
				}
				
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
