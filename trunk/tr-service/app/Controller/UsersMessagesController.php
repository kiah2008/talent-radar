<?php
App::uses('AppController', 'Controller');

class UsersMessagesController extends AppController {

	public $name = 'UsersMessages';
	
	public $components = array('GCMNotification');

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_getChats', 'app_getMessages', 'app_addMessage');
	}
	
	public function app_getChats() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$users = $this->UsersMessage->find('all', array('fields' => array('UsersMessage.user_from_id', 'UsersMessage.user_to_id', 'MAX(UsersMessage.created) as date'),
																					'conditions' => array('OR' => array('UsersMessage.user_from_id' => $this->data['UsersMessage']['user_id'], 'UsersMessage.user_to_id' => $this->data['UsersMessage']['user_id'])),
																					'group' => array('UsersMessage.user_from_id', 'UsersMessage.user_to_id'),
																					'order' => 'date desc'
																					));
			$usersIds = array();
			$dates = array();
			foreach($users as $user) {
				$userId = $usersIds[] = $this->data['UsersMessage']['user_id'] == $user['UsersMessage']['user_from_id'] ? $user['UsersMessage']['user_to_id'] : $user['UsersMessage']['user_from_id'];
				
				if(!isset($dates[$userId])) {
					$dates[$userId] = $user[0]['date'];
				}
			}
			
			$this->loadModel('User');
			$users = $this->User->find('all', array('recursive' => -1, 'fields' => array('User.id', 'User.username', 'User.name', 'User.surname', 'User.picture'), 'conditions' => array('User.id' => $usersIds)));
			
			foreach($users as $user) {
				$response['result']['chats'][] = $user;
				$response['result']['chats'][count($response['result']['chats'])-1]['date'] = $dates[$user['User']['id']];
			}
			
			$response['result']['status'] = 'ok';
			
			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_getMessages() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$limit = !empty($this->data['UsersMessage']['limit']) ? array('limit' => $this->data['UsersMessage']['limit']) : array();
			
			$conditions = array('conditions' => array('OR' => array(
																	array('UsersMessage.user_from_id' => $this->data['UsersMessage']['user1_id'], 'UsersMessage.user_to_id' => $this->data['UsersMessage']['user2_id']),
																	array('UsersMessage.user_from_id' => $this->data['UsersMessage']['user2_id'], 'UsersMessage.user_to_id' => $this->data['UsersMessage']['user1_id']))));
																	
			if(isset($this->data['UsersMessage']['first_message_id']) && !empty($this->data['UsersMessage']['first_message_id'])) {
				$conditions['conditions']['UsersMessage.id <'] = $this->data['UsersMessage']['first_message_id'];
			}
			
			$response['result']['messages'] = $this->UsersMessage->find('all', array_merge($limit, $conditions));
			
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
					$userFrom = $this->User->find('first', array('fields' => array('User.id', 'User.name', 'User.surname', 'User.username', 'User.picture'), 'conditions' => array('User.id' => $this->data['UsersMessage']['user_from_id'])));
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
