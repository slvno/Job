

CREATE TABLE notes_users (
    UserId            INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'Порядковый номер',	
    
    UserPatronymic    VARCHAR(99) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Фамилия', 	
    UserName          VARCHAR(99) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Имя',
    UserSurname       VARCHAR(99) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Отчество', 
    UserUID           VARCHAR(64) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Идентификатор',
    UserLogin         VARCHAR(64) CHARACTER SET utf8 DEFAULT '' NOT NULL UNIQUE COMMENT 'login',
    UserPassword      VARCHAR(64) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'password',
    UserShortName     VARCHAR(64) CHARACTER SET utf8 DEFAULT '' COMMENT 'Псевдоним', 
    UserEmail         VARCHAR(255) CHARACTER SET utf8 COMMENT 'Почта', 
    UserHomepage      VARCHAR(255) CHARACTER SET utf8 COMMENT 'Домашняя страница', 
    UserInformation   text CHARACTER SET utf8  COMMENT 'Информация',
    
    UserCreated       DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',
    UserModified      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
    UserDeleted       DATETIME DEFAULT NULL COMMENT 'Дата удаления',
    
    CONSTRAINT PK_NOTES_USERS PRIMARY KEY (UserId),    
    CONSTRAINT U_NOTES_USERS UNIQUE (UserName, UserSurname, UserPatronymic, UserShortName, UserEmail, UserHomepage,
    UserUID, UserLogin)
) COMMENT='Автор заметки' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

-- Получить название таблицы notes_users
SELECT table_comment 
FROM information_schema.tables
WHERE table_schema = 'flibusta' AND table_name = 'notes_users'; 
 
-- Получить название столбцов таблицы notes_users
SELECT column_name, column_comment, t.*
FROM information_schema.COLUMNS t
WHERE table_schema = 'flibusta' AND table_name = 'notes_users';
/*
SELECT 

ordinal_position position,
column_name name, 
column_comment comment, 

CASE
    WHEN data_type = 'datetime' THEN 20
    WHEN data_type = 'int' THEN 12
    WHEN data_type = 'varchar' THEN character_maximum_length
    WHEN data_type = 'text' THEN character_maximum_length
    WHEN character_maximum_length is null THEN 255
    ELSE 255
END size,

character_maximum_length, 

data_type, t.*
FROM information_schema.COLUMNS t
WHERE table_schema = 'flibusta' AND table_name = 'notes_users';

4 000 000 000
*/	
SELECT `notes_users`.`UserId`,
    `notes_users`.`UserPatronymic`,
    `notes_users`.`UserName`,
    `notes_users`.`UserSurname`,
    `notes_users`.`UserUID`,
    `notes_users`.`UserShortName`,
    `notes_users`.`UserEmail`,
    `notes_users`.`UserHomepage`,
    `notes_users`.`UserInformation`,
    `notes_users`.`UserCreated`,
    `notes_users`.`UserModified`,
    `notes_users`.`UserDeleted`
FROM `flibusta`.`notes_users`;

INSERT INTO `flibusta`.`notes_users`
(`UserId`,
`UserPatronymic`,
`UserName`,
`UserSurname`,
`UserUID`,
`UserShortName`,
`UserEmail`,
`UserHomepage`,
`UserInformation`,
`UserCreated`,
`UserModified`,
`UserDeleted`)
VALUES
(<{UserId: }>,
<{UserPatronymic: }>,
<{UserName: }>,
<{UserSurname: }>,
<{UserUID: }>,
<{UserShortName: }>,
<{UserEmail: }>,
<{UserHomepage: }>,
<{UserInformation: }>,
<{UserCreated: CURRENT_TIMESTAMP}>,
<{UserModified: CURRENT_TIMESTAMP}>,
<{UserDeleted: }>);

UPDATE `flibusta`.`notes_users`
SET
`UserId` = <{UserId: }>,
`UserPatronymic` = <{UserPatronymic: }>,
`UserName` = <{UserName: }>,
`UserSurname` = <{UserSurname: }>,
`UserUID` = <{UserUID: }>,
`UserShortName` = <{UserShortName: }>,
`UserEmail` = <{UserEmail: }>,
`UserHomepage` = <{UserHomepage: }>,
`UserInformation` = <{UserInformation: }>,
`UserCreated` = <{UserCreated: CURRENT_TIMESTAMP}>,
`UserModified` = <{UserModified: CURRENT_TIMESTAMP}>,
`UserDeleted` = <{UserDeleted: }>
WHERE `UserId` = <{expr}>;

DELETE FROM `flibusta`.`notes_users`
WHERE `UserId` = <{UserId: }>;

/notes/user/select/UserID/{UserId}
/notes/user/update/UserID/{UserId}
/notes/user/insert/UserLogin/{Login}
/notes/user/connect/UserLogin/{Login}
/notes/user/delete/UserLogin/{Login}

/notes/user/registration/UserLogin/{Login}


CREATE TABLE notes_chapters (
    ChapterId          INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'Chapter ID',
	ChapterParentId    INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Chapter Parent ID',
	UserId             INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
    
	ChapterName        VARCHAR(254) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Название раздела',
    ChapterDescription LONGTEXT CHARACTER SET utf8 NULL COMMENT 'Описание', 
	ChapterCreated     DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ChapterModified    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	ChapterDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_CHAPTERS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
	CONSTRAINT FK_NOTES_CHAPTERS_CHAPTERS FOREIGN KEY(ChapterParentId) REFERENCES notes_chapters(ChapterId),
	
    CONSTRAINT PK_NOTES_CHAPTERS PRIMARY KEY (ChapterId)
) COMMENT='Разделы' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;


CREATE TABLE notes_themes (
    ThemeId       INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'Theme ID',
	ChapterId     INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Chapter ID',
	UserId        INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
    ThemeName     VARCHAR(254) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Название темы',
    ThemeContent  LONGTEXT     CHARACTER SET utf8 NULL COMMENT 'Содержимое', 
	ThemeCreated  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ThemeModified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	ThemeDeleted       DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_THEMES_CHAPTERS FOREIGN KEY(ChapterId) REFERENCES notes_chapters(ChapterId),	
	CONSTRAINT FK_NOTES_THEMES_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
    
    CONSTRAINT PK_NOTES_THEMES PRIMARY KEY (ThemeId, ChapterId, UserId)
) COMMENT='Темы' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_theme_subscriptions (
    ThemeSubscriptionId       INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'Theme Subscription ID',
    ThemeId                   INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Theme ID',
	UserId                    INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
    ThemeSubscriptionCreated  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ThemeSubscriptionModified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	ThemeSubscriptionDeleted       DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_THEME_SUBSCRIPTIONS_THEMES FOREIGN KEY(ThemeId) REFERENCES notes_themes(ThemeId),
    CONSTRAINT FK_NOTES_THEME_SUBSCRIPTIONS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
      
    CONSTRAINT PK_NOTES_THEME_SUBSCRIPTIONS PRIMARY KEY (ThemeSubscriptionId, ThemeId, UserId)
) COMMENT='Подписка на тему' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_chapter_subscriptions (
    ChapterSubscriptionId       INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'Chapter Subscription ID',
    ChapterId                   INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Chapter ID',
	UserId                      INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
    ChapterSubscriptionCreated  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ChapterSubscriptionModified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	ChapterSubscriptionDeleted       DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_CHAPTER_SUBSCRIPTIONS_CHAPTERS FOREIGN KEY(ChapterId) REFERENCES notes_chapters(ChapterId),
	CONSTRAINT FK_NOTES_CHAPTER_SUBSCRIPTIONS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),  
	
    CONSTRAINT PK_NOTES_СHAPTER_SUBSCRIPTIONS PRIMARY KEY (ChapterSubscriptionId, ChapterId, UserId)
) COMMENT='Подписка на раздел' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_chapter_comments (
    ChapterСommentId        INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT  'Chapter Сomment Id', 
	ParentChapterСommentId INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Parent Chapter Сomment Id', 
    
	UserId                  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    Title                   VARCHAR(255) CHARACTER SET utf8 NULL  COMMENT 'Заголовок',
    Body                    LONGTEXT     CHARACTER SET utf8 NULL  COMMENT 'Содержимое',
	ChapterСommentCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ChapterСommentModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
    ChapterСommentDeleted       DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_CHAPTER_COMMENTS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
	CONSTRAINT FK_NOTES_CHAPTER_COMMENTS_CHAPTER_COMMENTS FOREIGN KEY(ParentChapterСommentId) REFERENCES notes_chapter_comments(ChapterСommentId),
	
	CONSTRAINT PK_NOTES_CHAPTER_COMMENTS PRIMARY KEY (ChapterСommentId, ParentChapterСommentId, UserId)
) COMMENT='Коментарии к разделу' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_theme_comments (
    ThemeСommentId          INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT  'Theme Сomment Id', 
	ParentThemeСommentId   INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Parent Theme Сomment Id', 
    
	UserId                  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    Title                   VARCHAR(255) CHARACTER SET utf8 NULL  COMMENT 'Заголовок', 
    Body                    LONGTEXT     CHARACTER SET utf8 NULL  COMMENT 'Содержимое',  
	ThemeСommentCreated     DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ThemeСommentModified    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
    ThemeСommentDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_THEME_COMMENTS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
	CONSTRAINT FK_NOTES_THEME_COMMENTS_THEME_COMMENTS FOREIGN KEY(ParentThemeСommentId) REFERENCES 
	notes_theme_comments(ThemeСommentId),
	
    CONSTRAINT PK_NOTES_THEME_COMMENTS PRIMARY KEY (ThemeСommentId, ParentThemeСommentId, UserId)
	
) COMMENT='Коментарии к теме' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_files (
    FileId         INT UNSIGNED  AUTO_INCREMENT NOT NULL COMMENT  'File Id', 
	UserId         INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	
	FileName       VARCHAR(255) CHARACTER SET utf8 NOT NULL COMMENT 'Имя файла',
	FilePath       TEXT CHARACTER SET utf8 NOT NULL COMMENT 'Путь к файлу',
	FileMd5        BINARY(32) NOT NULL COMMENT 'md5 сумма', 
	FileTime       DATETIME DEFAULT NOW() NOT NULL COMMENT 'время создания файла', 
    FileTimeLastModified   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'время изменения файла',	
	FileTitle      VARCHAR(254) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Название файла для ссылки', 
	FileLang       CHAR(3) CHARACTER SET utf8 DEFAULT 'ru' NULL COMMENT 'Язык файла', 
	FileType       CHAR(4) CHARACTER SET utf8 NULL  COMMENT 'Тип файла', 
	FileSrcLang    CHAR(3) CHARACTER SET utf8 DEFAULT '' NULL  COMMENT 'Исходный язык файла', 
    FileEncoding   VARCHAR(32) CHARACTER SET utf8 DEFAULT '' NULL  COMMENT 'Кодировка файла', 		 
	FileDeleted    CHAR(1) CHARACTER SET utf8 DEFAULT '0' NULL  COMMENT 'Файл удален', 
	FileAuthor     VARCHAR(255) CHARACTER SET utf8 NULL  COMMENT 'Автор файла', 
	FileVersion    VARCHAR(12) CHARACTER SET utf8 DEFAULT '' NULL  COMMENT 'Версия файла', 
	FileKeywords   VARCHAR(255) CHARACTER SET utf8 NULL  COMMENT 'Ключевые слова', 
	FileCreated    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	FileModified   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	FileDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_FILES_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
	
	CONSTRAINT PK_NOTES_FILES PRIMARY KEY (FileId, UserId), 
    UNIQUE (FileMd5)
) COMMENT='Файлы' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_pictures (
    PictureId        INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT  'Picture Id', 
    ThemeId          INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT  'Theme ID',     
    FileId           INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT  'File Id',	
	UserId           INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT  'User ID',
	
	PictureРosition  VARCHAR(32) DEFAULT ''  COMMENT 'Положение (например, position: relative; Или absolute, fixed, relative, static, inherit;)',
	PictureX         INT NOT NULL DEFAULT 0  COMMENT 'Смещение по горизонтали',
	PictureY         INT NOT NULL DEFAULT 0  COMMENT 'Смещение по вертикали',  
		
	PictureWidth     INT NOT NULL DEFAULT 0  COMMENT 'Ширина',
	PictureHeight    INT NOT NULL DEFAULT 0  COMMENT 'Высота',  
	
	PictureAlpha     INT UNSIGNED NOT NULL DEFAULT 100 COMMENT 'Прозрачность в %, (по умолчанию: filter: alpha(Opacity=100); opacity: 1.00;)',  
	PictureZIndex    INT NOT NULL DEFAULT 0 COMMENT 'Z-Index (по умолчанию: z-index: 1; position: relative;)',  
	PictureBackgroundColor: VARCHAR(32) NOT NULL DEFAULT 'rgba(255, 255, 255, 1.0)' COMMENT 'Цвет фона (по умолчанию: rgba(255, 255, 255, 1.0)',
	
	PictureCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	PictureModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	PictureDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_PICTURES_THEMES FOREIGN KEY(ThemeId) REFERENCES notes_themes(ThemeId),
	CONSTRAINT FK_NOTES_PICTURES_FILES  FOREIGN KEY(FileId)  REFERENCES notes_files(FileId),
	CONSTRAINT FK_NOTES_PICTURES_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
	CONSTRAINT PK_NOTES_PICTURES PRIMARY KEY (PictureId, ThemeId, FileId, UserId)
)  COMMENT='Изображения' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_links_chapters (
    LinkId       INT UNSIGNED NOT NULL COMMENT 'Link ID', 
    UserId  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	ChapterId     INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Chapter ID',
	    	
	LinkName     VARCHAR(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'Название ссылки',
	LinkURL      TEXT CHARACTER SET utf8 NULL COMMENT 'Адресс ссылки',
	
    LinkCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	LinkModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения'
	LinkDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_LINKS_CHAPTERS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),	
	CONSTRAINT FK_NOTES_LINKS_CHAPTERS_СHAPTERS FOREIGN KEY(ChapterId) REFERENCES notes_chapters(ChapterId),
	
	CONSTRAINT PK_NOTES_LINKS_CHAPTERS PRIMARY KEY (LinkId, ChapterId, UserId)
)  COMMENT='Ссылки'  ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_links_themes (
    LinkId       INT UNSIGNED NOT NULL COMMENT 'Link ID', 
	UserId  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	ThemeId          INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'Theme ID',	
	     	
	LinkName     VARCHAR(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'Название ссылки',
	LinkURL      TEXT CHARACTER SET utf8 NULL COMMENT 'Адресс ссылки',
	
    LinkCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	LinkModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения'
	LinkDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_LINKS_THEMES_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),	
	CONSTRAINT FK_NOTES_LINKS_THEMES_THEMES FOREIGN KEY(ThemeId) REFERENCES notes_themes(ThemeId),
	
	CONSTRAINT PK_NOTES_LINKS_THEMES PRIMARY KEY (LinkId, ThemeId, UserId)
)  COMMENT='Ссылки'  ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_links_theme_comments (
    LinkId       INT UNSIGNED NOT NULL COMMENT 'Link ID', 
    UserId  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	ThemeСommentId   INT UNSIGNED NOT NULL DEFAULT 0 COMMENT  'Theme Сomment Id', 
      	
	LinkName     VARCHAR(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'Название ссылки',
	LinkURL      TEXT CHARACTER SET utf8 NULL COMMENT 'Адресс ссылки',
	
    LinkCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	LinkModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения'
	LinkDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_LINKS_THEME_COMMENTS_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
	CONSTRAINT FK_NOTES_LINKS_THEME_COMMENTS_THEME_COMMENTS FOREIGN KEY(ThemeСommentId) REFERENCES notes_theme_comments(ThemeСommentId),
	
	CONSTRAINT PK_NOTES_LINKS_THEME_COMMENTS PRIMARY KEY (LinkId, ThemeСommentId, UserId)
)  COMMENT='Ссылки'  ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_links_pictures (
    LinkId       INT UNSIGNED NOT NULL COMMENT 'Link ID', 
    UserId  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	PictureId        INT UNSIGNED NOT NULL DEFAULT 0 COMMENT  'Picture Id', 
  
    	
	LinkName     VARCHAR(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'Название ссылки',
	LinkURL      TEXT CHARACTER SET utf8 NULL COMMENT 'Адресс ссылки',
	
    LinkCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	LinkModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения'
	LinkDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_LINKS_PICTURES_USERS FOREIGN KEY(UserId) REFERENCES notes_users(UserId),
	CONSTRAINT FK_NOTES_LINKS_PICTURES_PICTURES FOREIGN KEY(PictureId) REFERENCES notes_pictures(PictureId),
	
	CONSTRAINT PK_NOTES_LINKS_PICTURES PRIMARY KEY (LinkId, PictureId, UserId)
)  COMMENT='Ссылки'  ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;



CREATE TABLE notes_rates_themes (
    RateID  INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT  'Rate Id', 
	
    ThemeId INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'Theme ID',     
    UserId  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    Rate    INT NOT NULL DEFAULT 0 COMMENT 'Оценка', 

    RateCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	RateModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	RateDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_RATES_THEMES_THEMES FOREIGN KEY(ThemeId) REFERENCES notes_themes(ThemeId),	
	CONSTRAINT FK_NOTES_RATES_THEMES_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
    CONSTRAINT PK_RATES_THEMES PRIMARY KEY (RateID, ThemeId, UserId)
)  COMMENT='Оценки темы' ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE notes_rates_themes_comments (
    RateID  INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT  'Rate Id', 
	
    ThemeСommentId   INT UNSIGNED NOT NULL DEFAULT 0 COMMENT  'Theme Сomment Id',   
    UserId  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    Rate    INT NOT NULL DEFAULT 0 COMMENT 'Оценка', 

    RateCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	RateModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	RateDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_RATES_THEMES_COMMENTS_THEME_COMMENTS FOREIGN KEY(ThemeСommentId) REFERENCES notes_theme_comments(ThemeСommentId),	
	CONSTRAINT FK_NOTES_RATES_THEMES_COMMENTS_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
    CONSTRAINT PK_NOTES_RATES_THEMES_COMMENTS PRIMARY KEY (RateID, ThemeСommentId, UserId)
)  COMMENT='Оценки комментария к теме' ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE notes_reviews (
    ReviewID  INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT  'Review Id', 
	
    ThemeId      INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'Theme ID',
	UserId       INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'User ID',
	
	ReviewName   VARCHAR(255) CHARACTER SET utf8 NOT NULL COMMENT 'Название',         
    ReviewText   TEXT CHARACTER SET utf8 NOT NULL COMMENT 'Обзор',  
	
	ReviewCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	ReviewModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	ReviewDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_REVIEWS_THEMES FOREIGN KEY(ThemeId) REFERENCES notes_themes(ThemeId),
	CONSTRAINT FK_NOTES_REVIEWS_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
    CONSTRAINT PK_NOTES_REVIEWS PRIMARY KEY (ReviewID, ThemeId, UserId)
) COMMENT='Обзоры' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_sequences (
    SequenceId   INT UNSIGNED AUTO_INCREMENT NOT NULL  COMMENT 'Sequence ID',  
    UserId       INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'User ID',
	
	SequenceNameId   INT UNSIGNED NOT NULL  COMMENT 'Sequence Name ID',  

	SequenceNumber INT UNSIGNED NOT NULL  COMMENT 'Порядковый номер в списке',     
	SequenceLevel  INT DEFAULT 0 NOT NULL COMMENT 'Уровень', 
    SequenceType   INT UNSIGNED DEFAULT 0 NOT NULL COMMENT 'Вид',  
		
	SequenceCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	SequenceModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	SequenceDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_SEQUENCES_SEQUENCES_NAMES FOREIGN KEY(SequenceNameId)  REFERENCES notes_sequences_names(SequenceNameId),
	CONSTRAINT FK_NOTES_SEQUENCES_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
	CONSTRAINT PK_SEQUENCES PRIMARY KEY (SequenceId, UserId, SequenceNameId)
) COMMENT='Последовательный cписок' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_sequences_names (
    SequenceNameId   INT UNSIGNED AUTO_INCREMENT NOT NULL  COMMENT 'Sequence Name ID', 
	UserId       INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    SequenceName VARCHAR(254) CHARACTER SET utf8 DEFAULT '' NOT NULL COMMENT 'Название', 

    SequenceNameCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	SequenceNameModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	SequenceNameDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_SEQUENCES_NAMES_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
    CONSTRAINT PK_SEQUENCES_NAMES PRIMARY KEY (SequenceNameId, UserId)
) COMMENT='Последовательный список названий' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

CREATE TABLE notes_sequences_themes (
    SequenceThemeId  INT UNSIGNED AUTO_INCREMENT NOT NULL  COMMENT 'Sequence Theme ID',
	UserId       INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    SequenceId INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Sequence ID', 
    ThemeId    INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Theme ID',
	
    SequenceThemeNumber  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Порядковый номер темы в списке', 

    SequenceThemeCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	SequenceThemeModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	SequenceThemDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_SEQUENCES_THEMES_SEQUENCES FOREIGN KEY(SequenceId)  REFERENCES notes_sequences(SequenceId),
	CONSTRAINT FK_NOTES_SEQUENCES_THEMES_THEMES FOREIGN KEY(ThemeId) REFERENCES notes_themes(ThemeId),	
	CONSTRAINT FK_NOTES_SEQUENCES_THEMES_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
    CONSTRAINT PK_SEQUENCES_THEMES PRIMARY KEY (SequenceThemeId, UserId, SequenceId, ThemeId)
) COMMENT='Последовательность названий тем' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;


CREATE TABLE notes_sequences_chapters (
    SequenceChapterId  INT UNSIGNED AUTO_INCREMENT NOT NULL  COMMENT 'Sequence Chapter ID',
	UserId       INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT 'User ID',
	
    SequenceId INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Sequence ID',     
	ChapterId     INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Chapter ID',
	
    SequenceChapterNumber  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Порядковый номер раздела в списке', 

    SequenceChapterCreated   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата создания',	
	SequenceChapterModified  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT 'Дата изменения',
	SequenceChapterDeleted     DATETIME DEFAULT NULL COMMENT 'Дата удаления',
	
	CONSTRAINT FK_NOTES_SEQUENCES_CHAPTERS_SEQUENCES FOREIGN KEY(SequenceId)  REFERENCES notes_sequences(SequenceId),
	CONSTRAINT FK_NOTES_SEQUENCES_CHAPTERS_CHAPTERS FOREIGN KEY(ThemeId) REFERENCES notes_chapters(ChapterId),	
	CONSTRAINT FK_NOTES_SEQUENCES_CHAPTERS_USERS  FOREIGN KEY(UserId)  REFERENCES notes_users(UserId),
	
    CONSTRAINT PK_SEQUENCE_CHAPTERS PRIMARY KEY (SequenceChapterId, UserId, SequenceId, ChapterId)
) COMMENT='Последовательность названий разделов' ENGINE=InnoDB DEFAULT CHARSET=UTF8 COLLATE utf8_unicode_ci;

