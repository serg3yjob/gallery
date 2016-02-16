$(init);

//Функция выполняющаяся после загрузки страницы
function init() {
	// setEqualHeight($(".sidebar, .content"));
	initImagesPage();
	initDialogSignUpInForm();
	initAlbomPage();
	initAdministratePage();
	initUserSettingsPage();
	initChangePasswordPage();
	initChangePasswordRestorePage();
	initChangeEmailPage();
	initRestorePage();
}
function initImagesPage() {
//В зависимости от того авторизован пользователь или нет установить размер блока списка меню 
//Нужно для того чтобы при авторизованном пользователе список пользователей не прыгал из за изменения стилей левого меню
	var lenghWelcomeHeader = $('#welcomeLabel').length;
	if(lenghWelcomeHeader == 1)$('#menu > ul').css('min-height', "180px")
	else $('#menu > ul').css('min-height', "30px");
	$('.blockedImgPgItem').click(function(e) {
		e.preventDefault();
	})
}
//Установка колонкам однинаковой высоты
function setEqualHeight(columns){
    var tallestcolumn = 0;
    columns.each(function(){
        currentHeight = $(this).height();
        console.log('tallestcolumn = ' +currentHeight);
        if(currentHeight > tallestcolumn){
            tallestcolumn = currentHeight;
        }
    });
    console.log('tallestcolumn = ' + tallestcolumn);
    columns.height(tallestcolumn);
}
//Инициализировать диалог для формы регистрации и авторизации
function initDialogSignUpInForm(){
	//Отображать диалоги только в том случае если установлен флаг об ошибке валидации	
	var showDialogLogin = false;
	if($('#signInForm').attr('data-loginerror') == 'true') showDialogLogin = true;
	var showDialogRegistration = false;
	if($('#signUpForm').attr('data-regerror') == 'true') showDialogRegistration = true;
	$('#wrapperSignUpForm').dialog({
	height: 410,
	width: 660,
	autoOpen: showDialogRegistration,
	resizable: false,
	open: function(){
		$('#signUpButton').button();
		$('#signUpCancelButton').button().click(function(){
			$('#wrapperSignUpForm').dialog('close');
		});
		$('#signUpForm').validate({
			rules: {
				userName: {
					required: true,
					rangelength: [2, 14]
				},
				email: {
					required: true,
					email: true
				},
				userPassword: {
					required: true,
					minlength: 6
				},
				userRetryPassword: {
					equalTo: '#userPassword'
				}

			},
			messages: {
				userName: {
					required: 'Поле "Ваше имя" обязательно для заполнения',
					rangelength: 'Поле "Ваше имя" должно содержать от 2 до 14 символов'
				},
				email: {
					required: 'Введите адрес электронной почты',
					email: 'Поле "Почта" должно содержать адрес электронной почты'
				},
				userPassword: {
					required: 'Введите пароль',
					minlength: 'Пароль должен содержать не менее 6 символов'
				},
				userRetryPassword: {
					equalTo: 'Подтверждение пароля не совпадает с введеным паролем'
				}
			},
			errorPlacement: errorMsgValidFormOutput
		});
	},
	close: loadBasePage
	});
	$('#wrapperSignInForm').dialog({
	height: 320,
	width: 420,
	autoOpen: showDialogLogin,
	resizable: false,
	open: function(){
		$('#signInButton').button();
		$('#signInCancelButton').button().click(function(){
			$('#wrapperSignInForm').dialog('close');
		});
		$('#signInForm').validate({
			rules: {
				userName: {
					required: true,
					rangelength: [2, 14]
				},
				userPassword: {
					required: true,
					minlength: 6
				}				
			},
			messages: {
				userName: {
					required: 'Введите имя',
					rangelength: 'Имя должно содержать от 2 до 14 символов'
				},
				userPassword: {
					required: 'Введите пароль',
					minlength: 'Пароль должен содержать не менее 6 символов'
				},
			},
			errorPlacement: errorMsgValidFormOutput

		});
	},
	close: loadBasePage
	});	
	$('#sign-up').click(
		function() {
			//Удалить сообщения валидации выведенные при открытии окон ранее
			$('#signUpForm .errorMessage').empty();
			$('#wrapperSignUpForm').dialog('open');
	});
	$('#sign-in').click(
		function() {
			//Удалить сообщения валидации выведенные при открытии окон ранее
			$('#signInForm .errorMessage').empty();
			$('#wrapperSignInForm').dialog('open');
	});	
	//Загрузка основной страницы после того как диалоги закрыты
	function loadBasePage(){
		var loc = window.location.href;
		if(loc.indexOf('registrate') > 0 || loc.indexOf('error') > 0 || loc.indexOf('restorepassword') > 0)
			loc = loc.substr(0, loc.lastIndexOf('/'));
			window.location.href = loc.substr(0, loc.lastIndexOf('/'));
	}
}
// Метод выводящий сообщения об ошибках валидации форм
function errorMsgValidFormOutput(error, element) {
	var serchSelector = 'em[data-for="' + element.attr('name') + '"]'
	var scopeSerch = element.parent().parent();
	var errorPlace = scopeSerch.find(serchSelector);
	errorPlace.empty();
	var errorText = error.text();
	errorPlace.append(errorText);
}
// Инициализация страницы альбома
function initAlbomPage() {
	$(document).delegate('.sendCommentBtn', 'click', handelSubmitForm);
	$(document).delegate('.commentFile', 'change', handelChangeFile);
	$(document).delegate('.addFileBtn', 'click', handelClickFileBtn);
	// Обработка вывода формы добавления ответа к комментарию
	$('.answerCommentBtn').click(function(event) {
		$('#addCommentFormAnswer').remove();
		var htmlFormAnswerComment;
		var parentCommentId = $(event.currentTarget).attr('data-comment-id');
		var addCommentForm = $('#addCommentForm');
		var idUserOwner = addCommentForm.attr('data-idUserOwner');
		var idUserGuest = addCommentForm.attr('data-idUserGuest');
		var numberPage = addCommentForm.attr('data-numberPage');
		var htmlFormAnswerComment = '' +
		'<form id="addCommentFormAnswer" class="addCommentForm" action="' + $('#addCommentForm').attr('action') + '" method="post" enctype="multipart/form-data">' +
			'<textarea name="comment" class="addingText commonText" placeholder="Введите комментарий" rows="4"></textarea><br/>' +
			'<div class="sendCommentBtn commonText itemLineBtnCommentForm">Добавить комментарий</div>' +
			'<div id="cancelAnswer" class="commonText itemLineBtnCommentForm">Отмена</div>' +
			'<label for="commentFileAnswer" id="addFileBtn" class="addFileBtn itemLineBtnCommentForm" title="Добавить файл"></label>' +
			'<input id="commentFileAnswer" class="commentFile" type="file" name="commentFile" />' +
			'<input name="userId" type="hidden" value="' + idUserOwner + '"/>' +
			'<input name="userGuestId" type="hidden" value="' + idUserGuest + '"/>' +
			'<input name="numberPage" type="hidden" value="' + numberPage + '"/>' +			
			'<input type="hidden" name="parentCommentId" value="' + parentCommentId + '"/>' +
		'</form>';
		$(event.currentTarget).parent().parent().append(htmlFormAnswerComment);
		$('#cancelAnswer').click(function(event) {
			$(event.currentTarget).parent().remove();
		});
	});
	// Обработка загрузки файлов изображений
	$('#imgFiles').change(function(event) {
		$('#addImageForm').submit();
	});
	// Обработка щелчка по кнопке +5 к изображению
	$('#marked').click(function(event) {
		if(!markImage(event)){
			$().toastmessage('showToast', {
			    text     : 'Вы уже оценивали это изображение ранее!',
			    sticky   : false,
			    type     : 'warning'
			});
		}else {
			$().toastmessage('showToast', {
			    text     : 'Вы добавили +5 к оценке этого изображения',
			    sticky   : false,
			    type     : 'success'
			});
		}
	});
	$('#setTitleDialog').dialog({
		height: 150,
		width: 600,
		autoOpen: false,
		resizable: false,
		buttons: [ {
						text: 'OK',
						click: function() {
							var target = $('#setTitleImgForm').find('input');
							text = target.val();
							if(text.length > 0 && !text.match(/^\s*$/)){
								$('#setTitleImgForm').submit();
							}
						}
					},
					 {
						text: 'Отмена',
						click: function() {
							$('#setTitleDialog').dialog('close');
						}
				}]
		});
	$('#setTitleImgAlbom').click(function() {
		$('#setTitleDialog').dialog('open');
	});
	$('#followAlbom').click(function(event) {
		var target = $(event.currentTarget);
		var url = target.attr('data-url');
		var idUserOwner = target.attr('data-idUserOwner');
		var idUserGuest = target.attr('data-idUserGuest');
		url = url + '/' + idUserOwner + '/' + idUserGuest;
		$.ajax({
			url: url,
			type: 'GET',
			data: null,
			async: true,
			success: function(data){
				if(data = 'ok'){
					target.parent().html('<div id="labelFollow">Вы подписаны на обновления этого альбома</div>');
				}
			}
		});
	});
}
// Функция проверки возможности поставить оценку, функция заправшивает у 
//сервера может ли текущий пользователь добавить +5 к оценке изображения, и в случае возможности добавляет +5 к оценке изображения
function markImage(event) {
	var target = event.currentTarget;
	var url = $(target).attr('data-url');
	var idUserOwner = $(target).attr('data-idUserOwner');
	var idUserGuest = $(target).attr('data-idUserGuest');
	var imageId = $(target).attr('data-imageId');
	var url = url + '/' + idUserOwner + '/' + idUserGuest + '/' + imageId;
	var successMark = false;
	$.ajax({
		type: 'GET',
		url: url,
		data: null,
		async: false,
		success: function(data) {
			if(data != 'false') {
				$(target).parent().find('#totallMark').text(data);
				successMark = true;
			}}
	});
	return successMark;
}
// Обработка отправки комментария
var valueCommentFile = '';
function handelSubmitForm(event){
	var target = $(event.currentTarget);
	var commentText = target.parent().find('textarea').val();
	if(commentText.length > 0 && !commentText.match(/^\s*$/)){
		target.parent().submit();
	}
}
// Добавление файла
function handelChangeFile(event) {
	var target = $(event.currentTarget);
	if(valueCommentFile.length == 0){
		var pathToImgForFileComment = $('#addCommentForm').attr('data-img-for-comment-file');
		$('<img src="' + pathToImgForFileComment + '" class="imgCommentFile itemLineBtnCommentForm" title="Добавленный файл"/>').insertBefore(target);
		$('.imgCommentFile').click(handelClickFileBtn);
		target.parent().find('img').click(function() {
			$('#confirmDeleteFile').dialog({
				height: 160,
				width: 300,
				autoOpen: false,
				resizable: false,
				buttons: [ {
								text: 'Удалить',
								click: function() {
									target.parent().find('img').remove();
									target.val('');
									$('#confirmDeleteFile').dialog('close');
							}
							}, {
								text: 'Отмена',
								click: function() {
									$('#confirmDeleteFile').dialog('close');
								}
							}
						]
			});
			$('#confirmDeleteFile').dialog('open');
			$('#confirmDeleteFile').css('display', 'block');
		});
	}
}
// Щелчок по кнопке "Добавить файл", нужно для того чтобы запомнить добавленное значение для файла
// в глобальной переменной
function handelClickFileBtn(event){
	var target = $(event.currentTarget);
	var valueFile = target.parent().find('input').val();
	valueCommentFile = valueFile;
}
// Инициализация страницы списка пользователей
function initAdministratePage() {
	$('#serchAdminUserBtn').click(function(event) {
		var target = $(event.currentTarget).parent().find('input');
		textSerch = target.val();
		if(textSerch.length > 0 && !textSerch.match(/^\s*$/)){
			target.parent().submit();
		}
	});
	var url = $('#serchAdminUserInput').attr('data-autocomplite-action');
	$('#serchAdminUserInput').autocomplete({
		source: url,
		minLength: 2,
		select: function() {
//			$('#serchAdminUserInput').parent().submit(); функция выполняется до того как атрибут value эл-та input успеет измениться
		}
	});
}
// Инициализация страницы настроек пользователя
function initUserSettingsPage() {
	// Обработка загрузки файла аватара
	$('#settingsAvatarFile').change(function(event) {
		$(event.currentTarget).parent().submit();
	});
}
// Инициализация страницы смены пароля
function initChangePasswordPage() {
	$('#settingsChangePasswordForm').validate({
		rules: {
			oldUserPassword: {
				required: true,
				minlength: 6
			},
			userPassword: {
				required: true,
				minlength: 6
			},			
			settingsUserRetryPassword: {
				equalTo: '#settingsUserPassword'
			}

		},
		messages: {
			oldUserPassword: {
				required: 'Введите действующий пароль',
				minlength: 'Пароль должен содержать не менее 6 символов'
			},			
			userPassword: {
				required: 'Введите новый пароль',
				minlength: 'Пароль должен содержать не менее 6 символов'
			},
			settingsUserRetryPassword: {
				equalTo: 'Подтверждение пароля не совпадает с введеным паролем'
			}
		},
		errorPlacement: errorMsgValidFormOutput
	});
	$('#settingsChangePasswordBtn').click(function(event){
		$(event.currentTarget).parent().submit();
	});
}
//Инициализация страницы смены пароля
function initChangePasswordRestorePage() {
	$('#settingsChangePasswordRestoreForm').validate({
		rules: {
			userPassword: {
				required: true,
				minlength: 6
			},			
			settingsUserRetryPassword: {
				equalTo: '#settingsUserPassword'
			}

		},
		messages: {	
			userPassword: {
				required: 'Введите новый пароль',
				minlength: 'Пароль должен содержать не менее 6 символов'
			},
			settingsUserRetryPassword: {
				equalTo: 'Подтверждение пароля не совпадает с введеным паролем'
			}
		},
		errorPlacement: errorMsgValidFormOutput
	});
	$('#settingsChangePasswordBtn').click(function(event){
		$(event.currentTarget).parent().submit();
	});
}
// Инициализация страницы смены e-mail
function initChangeEmailPage() {
	$('#settingsChangeEmailForm').validate({
		rules: {
			email: {
				required: true,
				email: true
			}
		},
		messages: {
			email: {
				required: 'Введите адрес электронной почты',
				email: 'Поле "Почта" должно содержать адрес электронной почты'
			}
		},
		errorPlacement: errorMsgValidFormOutput
	});
	$('#settingsChangeEmaildBtn').click(function(event){
		$(event.currentTarget).parent().submit();
	});	
}
// Инициализация страницы восстановления доступа
function initRestorePage() {
	$('#restoreForm').validate({
		rules: {
			email: {
				required: true,
				email: true
			}
		},
		messages: {
			email: {
				required: 'Введите адрес электронной почты',
				email: 'Поле "Почта" должно содержать адрес электронной почты'
			}
		},
		errorPlacement: errorMsgValidFormOutput
	});
	$('#settingsRestoreBtn').click(function(event){
		$(event.currentTarget).parent().submit();
	});	
}