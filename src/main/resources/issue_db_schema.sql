CREATE TABLE issues (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    issue_desc TEXT NOT NULL,
    issue_type VARCHAR(50) NOT NULL,
    root_cause TEXT,
    resolution TEXT,
    resolved BOOLEAN DEFAULT FALSE,
    refs TEXT,
    created_at DATETIME NOT NULL
);
-- for user id 2
INSERT INTO issues (user_id, issue_desc, issue_type, root_cause, resolution, resolved, refs, created_at)
VALUES
(2, 'Password Reset failed!!', 'API call',
 'Function failed due to improper failure handling', '',
 FALSE, '', STR_TO_DATE('22 May 2024 17:10', '%d %M %Y %H:%i')),

(2, 'Does onchange event trigger at onload also ?', 'UI',
 'Question', '', FALSE, '', STR_TO_DATE('31 May 2024 17:10', '%d %M %Y %H:%i')),

(2, 'JSONObject data is always empty in spring boot post call', 'Code',
 'Springboot is not able to convert JSONObject to json, since the library is unknown to springboot',
 'Workaround is to use String and then create JSONObject from it or use Map',
 TRUE, 'https://stackoverflow.com/questions/53258297/access-to-xmlhttprequest-has-been-blocked-by-cors-policy',
 STR_TO_DATE('17 May 2024 14:10', '%d %M %Y %H:%i')),

(2, 'Access to XMLHttpRequest has been blocked by CORS policy via browser', 'API call',
 'It''s a problem with the privacy of your browser',
 'add an annotation @Crossorigin right above the CRUD api call method',
 TRUE, 'https://stackoverflow.com/questions/53258297/access-to-xmlhttprequest-has-been-blocked-by-cors-policy, https://stackoverflow.com/questions/53258297/access-to-xmlhttprequest-has-been-blocked-by-cors-policy',
 STR_TO_DATE('11 May 2024 09:40', '%d %M %Y %H:%i')),

(2, 'Error in pom.xml when adding non string boot starter dependecy', 'Code',
 'Springboot parent not able to recognize this library',
 'Need to add version',
 TRUE, 'https://stackoverflow.com/questions/53258297/access-to-xmlhttprequest-has-been-blocked-by-cors-policy',
 STR_TO_DATE('18 May 2024 10:10', '%d %M %Y %H:%i')),

(2, 'Not able to compute previous row data in jsp/jstl dynamic generated table, the same functionality works on change had to be worked on page load', 'UI',
 'Jsp/el no option, had to it in javascript/jquery',
 'called the onchange event on the rows on page load, workaround',
 TRUE, 'https://stackoverflow.com/questions/78482275/computing-next-row-value-based-on-previous-in-jsp-jstl-in-jquery-for-a-dynamical',
 STR_TO_DATE('21 May 2024 12:30', '%d %M %Y %H:%i')),

(2, 'Quarkus junit test was not able to perform on active mq listeners', 'Code',
 'Quarkus Junit - Apache''s Producer template mocking isn''t working',
 '', FALSE, 'https://stackoverflow.com/questions/73962011/quarkus-junit-apaches-producer-template-mocking-isnt-working',
 STR_TO_DATE('20 May 2024 11:20', '%d %M %Y %H:%i')),

(2, 'Jquery Uncaught SyntaxError: Unexpected token } - Trying to pass guid as parameter', 'UI',
 'somehow normal quoting not working for add dynamic value in a function',
 'need to escape stating and closing quotes like onclick=edit( + row.id + )',
 TRUE, 'https://stackoverflow.com/questions/20976735/jquery-uncaught-syntaxerror-unexpected-token-trying-to-pass-guid-as-paramet',
 STR_TO_DATE('17 May 2024 08:33', '%d %M %Y %H:%i')),

(2, 'Update assessment was stuck in java code and oracle db (DB got locked)', 'DataBase',
 'insert data query was not commited in oracle sql developer',
 'after committing insert query, db got unlocked',
 TRUE, 'https://stackoverflow.com/questions/2276283/sql-update-hangs-java-program',
 STR_TO_DATE('22 May 2024 16:20', '%d %M %Y %H:%i')),

(2, 'Has been blocked by CORS policy: Response to preflight request doesn’t pass access control check', 'UI',
 'Chrome was blocking request from different origin',
 'configuration.setAllowedOrigins(Arrays.asList(*)); Add allowed-origin header in resource server in API Gateway',
 TRUE, 'https://stackoverflow.com/questions/78606679/unable-to-install-and-use-nodejs-using-fnm/78619685, https://github.com/Schniz/fnm#shell-setup',
 STR_TO_DATE('31 May 2024 17:10', '%d %M %Y %H:%i')),

(2, 'Could not find acceptable representation using jquery ajax', 'API call',
 'HttpMediaTypeNotAcceptableException: Could not find acceptable representation',
 '', FALSE, '', STR_TO_DATE('21 June 2024 22:41', '%d %M %Y %H:%i')),

(2, 'Update ids with this save', 'Code',
 '', '', FALSE, '', STR_TO_DATE('28 June 2024 23:09', '%d %M %Y %H:%i')),

(2, 'Effective final cond.', 'Code',
 '', '', FALSE, '', STR_TO_DATE('09 July 2024 03:48', '%d %M %Y %H:%i')),

(2, 'REACT Cors issue while hitting backend rest api', 'API call',
 'Chrome browser cors issue',
 'Add  mode: ''no-cors'' in react ajax call',
 TRUE, 'https://stackoverflow.com/questions/58403651/react-component-has-been-blocked-by-cors-policy-no-access-control-allow-origin',
 STR_TO_DATE('21 July 2024 21:30', '%d %M %Y %H:%i')),

(2, 'React POST net::ERR_ABORTED 415 (Unsupported Media Type)', 'UI',
 'usage of mode: no-cors in react for cors issue',
 'Remove mode: no-cors and use Chrome cors extention for cors issue',
 TRUE, 'https://stackoverflow.com/questions/61854655/post-neterr-aborted-415-unsupported-media-type',
 STR_TO_DATE('22 July 2024 02:07', '%d %M %Y %H:%i')),

(2, 'React component getting rendered thrice', 'UI',
 'use of strictMode ,state being changed',
 '1. Removed <React.StrictMode>, but still rendered twice, 2. If state is being updated while initial load, then react will rerender the component',
 FALSE, 'https://stackoverflow.com/questions/35136836/react-component-render-is-called-multiple-times-when-pushing-new-url',
 STR_TO_DATE('22 July 2024 02:37', '%d %M %Y %H:%i')),

(2, 'error 1045 (28000): access denied for user ''odbc''@''localhost'' (using password: no)', 'DataBase',
 'For some reason, the ODBC user is the default username under windows even if you didn''t create that user at setup time.',
 'use command: mysql -u root -p',
 TRUE, 'https://stackoverflow.com/questions/23950722/how-to-overcome-error-1045-28000-access-denied-for-user-odbclocalhost-u',
 NOW()),

(2, 'error 1045 (28000): access denied for user ''odbc''@''localhost'' (using password: no)..........................................................', 'DataBase',
 'For some reason, the ODBC user is the default username under windows even if you didn''t create that user at setup time.',
 'use command: mysql -u root -p',
 TRUE, 'https://stackoverflow.com/questions/23950722/how-to-overcome-error-1045-28000-access-denied-for-user-odbclocalhost-u',
 STR_TO_DATE('21 December 2025 22:52', '%d %M %Y %H:%i')),

(2, 'email template testing', 'UI',
 'template not foundssss',
 'apache free marker , template loader ?',
 FALSE, 'ftl',
 STR_TO_DATE('18 January 2026 02:52', '%d %M %Y %H:%i')),

(2, 'email template testing', 'UI',
 'template not foundssss',
 'apache free marker , template loader ?',
 FALSE, 'ftl',
 STR_TO_DATE('18 January 2026 02:52', '%d %M %Y %H:%i')),

(2, 'Freemarker not loading email (ftl) templates', 'Configuration',
 'Config was not done correctly.',
 'Created custom config file for freemarker to load templates',
 TRUE, 'https://stackoverflow.com/questions/8910271/how-can-i-reference-a-commit-in-an-issue-comment-on-github',
 STR_TO_DATE('18 January 2026 03:09', '%d %M %Y %H:%i')),

(2, 'Silent Exception while starting server in debug mode', 'Code',
 'Dev tools dependency is added when',
 '', FALSE, '', STR_TO_DATE('18 January 2026 03:14', '%d %M %Y %H:%i'));
 
 
 -- for user id 1
 INSERT INTO issues (user_id, issue_desc, issue_type, root_cause, resolution, resolved, refs, created_at) VALUES
(1, 'Enable Tags and Tagging', 'Code', '', '', FALSE, '', STR_TO_DATE('28 June 2024 22:42', '%d %M %Y %H:%i')),
(1, 'Keep id 5 digit hexadecimal only instead of uuid', 'Code', '', 'Implemented', TRUE, 'https://stackoverflow.com/questions/50904587/java-how-to-generate-a-6-digit-random-hexadecimal-value', STR_TO_DATE('27 June 2024 01:28', '%d %M %Y %H:%i')),
(1, 'Add quick note page for development or any other work', 'Code', '', 'Added', TRUE, '', STR_TO_DATE('27 June 2024 01:27', '%d %M %Y %H:%i')),
(1, 'Change password/ Forgot password func.', 'Code', '', '', FALSE, '', STR_TO_DATE('10 December 2025 04:18', '%d %M %Y %H:%i'));
