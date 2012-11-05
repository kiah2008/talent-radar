<?php
App::uses('AppController', 'Controller');
CakePlugin::load('Linkedin');

class UsersBannedController extends AppController {

	public $name = 'UsersBanned';
	public $uses = array('UsersBanned');
	
	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_getBanned', 'app_deleteBanned');
	}
	
	public function app_getBanned()
	{
		if(!empty($this->data))
		{
			$response['status'] = 'ok';
			$response['result']['status'] = 'ok';
			
			$response['result']['users'] = $this->UsersBanned->find('all', array('conditions' => array('UsersBanned.user_from_id' => $this->data['UsersBanned']['user_from_id'])));
			
			$this->set('response', $response);
		}
		else
		{
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_deleteBanned()
	{
		if(!empty($this->data))
		{
			$response['status'] = 'ok';
			
			$this->UsersBanned->deleteAll(array('UsersBanned.id' => $this->data['UsersBanned']['id'], 'UsersBanned.user_from_id' => $this->data['UsersBanned']['user_from_id']));
			$response['result']['status'] = 'ok';
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('ids', $this->UsersBanned->find('list', array('fields' => array('id', 'id'))));
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
