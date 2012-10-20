<?php

App::uses('AppController', 'Controller');
CakePlugin::load('Linkedin');
class UsersController extends AppController {

	public $name = 'Users';
	
	var $components = array('Linkedin.Linkedin');

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_getUser', 'app_register', 'app_login', 'app_setPrivacy', 'app_loginLinkedin', 'app_loginLinkedinCallback', 'app_loginLinkedinAuthorizeCallback', 'app_android_saveDeviceId');
	}
	
	public function app_getUser() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($response['result']['user'] = $this->User->find('first', array('fields' => array('id', 'auth_token', 'email', 'username', 'name', 'surname', 'headline', 'picture', 'show_name', 'show_headline', 'show_skills', 'show_in_searches'), 'conditions' => array('User.id' => $this->data['User']['id'])))) {
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_register() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$this->User->activateValidation();
			$this->User->set($this->data);
			if($ok = ($invalidFields = $this->User->invalidFields()) ? false : true)
			{
				$this->request->data['User']['password'] = $this->Auth->password($this->request->data['User']['password']);
				if($ok = $this->User->save($this->data)) {
					$response['result']['status'] = 'ok';
				}
			}

			if(!$ok) {
				$response['result']['invalidFields'] = $invalidFields;
			}

			$this->set('response', $response);
		}
	}
	
	public function app_login() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($response['result']['user'] = $this->User->find('first', array('fields' => array('id', 'auth_token', 'email', 'username', 'name', 'surname', 'headline', 'picture', 'show_name', 'show_headline', 'show_skills', 'show_in_searches'), 'conditions' => array('User.email' => $this->data['User']['email'], 'User.password' => $this->Auth->password($this->data['User']['password']))))) {
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
	}
	
	public function app_setPrivacy() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($user = $this->User->read(null, $this->data['User']['id'])) {
				if(isset($this->data['User']['username'])) {
					$user['User']['username'] = $this->data['User']['username'];
				}
				$user['User']['show_name'] = $this->data['User']['show_name'];
				$user['User']['show_headline'] = $this->data['User']['show_headline'];
				$user['User']['show_skills'] = $this->data['User']['show_skills'];
				$user['User']['show_in_searches'] = $this->data['User']['show_in_searches'];
				$this->User->set($user);
				if($this->User->save($this->data)) {
					$response['result']['status'] = 'ok';
				}	
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
		
	}
	
	public function app_android_saveDeviceId() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$this->User->id = $this->data['User']['id'];
			if($this->User->saveField('android_device_id', $this->data['User']['android_device_id'])) {
				$response['result']['status'] = 'ok';
				$response['result']['User'] = $this->data['User'];
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('ids', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_loginLinkedin() {
		$this->Linkedin->connect(array('action' => 'app_loginLinkedinCallback'));
	}
	
	public function app_loginLinkedinCallback() {
		if(isset($_GET['oauth_token']))
		{
			$this->Linkedin->authorize(array('action' => 'app_loginLinkedinAuthorizeCallback'));
		}
		elseif(isset($_GET['oauth_problem']))
		{
			$this->redirect('talent.call.linkedin.back://https:/error='.$_GET['oauth_problem']);
		}
	}


	public function app_loginLinkedinAuthorizeCallback() {
		$linkedinToken = $this->Linkedin->getKeyAndSecretOfUser();
		if(!empty($linkedinToken)) {
			if(!$user = $this->User->find('first', array('conditions' => array('User.linkedin_key' => $linkedinToken['linkedin_key'], 'User.linkedin_secret' => $linkedinToken['linkedin_secret']))))
			{
				$user['User'] = $linkedinToken;
			}
			
			$data = $this->Linkedin->call('people/~',
													   array(
													        'first-name', 'last-name', 'headline', 'picture-url'
													   ));
			if(isset($data['person']))
			{
				$user['User']['name'] = isset($data['person']['first-name']) ? $data['person']['first-name'] : '';
				$user['User']['surname'] = isset($data['person']['last-name']) ? $data['person']['last-name'] : '';
				$user['User']['headline'] = isset($data['person']['headline']) ? $data['person']['headline'] : '';
				$user['User']['picture'] = isset($data['person']['picture-url']) ? $data['person']['picture-url'] : '';
			}
			
			$user = $this->User->save($user, false);
			
			$this->redirect('talent.call.linkedin.back://https:/'.$user['User']['id']);
		}
		exit;
		/*debug($this->Linkedin->call('people/~',
			   array(
			        'id',
			        'picture-url',
			        'first-name', 'last-name', 'summary', 'specialties', 'associations', 'honors', 'interests', 'twitter-accounts',
			        'positions' => array('title', 'summary', 'start-date', 'end-date', 'is-current', 'company'),
			        'educations',
			        'certifications',
			        'skills' => array('id', 'skill', 'proficiency', 'years'),
			        'recommendations-received',
			   )));
		exit;*/
	}
}
