grammar PulseRequest;

requestFile: (request)* EOF ;

request: NEW_LINE? requestName httpMethod url NEW_LINE headers? body? ;

httpMethod: HTTP_METHOD;

HTTP_METHOD: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH' ;

url: URL;

URL: 'http' 's'? '://' ~[\r\n\t ]+ ;

headers: (header)+ ;

header: headerName ':' headerValue NEW_LINE? ;

NEW_LINE: '\r'? '\n' ;

requestName: REQUEST_NAME NEW_LINE;

REQUEST_NAME : '[' ('a'..'z'|'A'..'Z'| '_' | '-' | ' ')+ ']' ;

ALPHA: [A-Za-z];

DIGIT: [0-9];

tchar: '!' |   '#' |   '$' |   '%' |   '&' |   '\'' | '/'  | '*' |   '+' |   '-' |   '.' |   '^' |   '_' |   '`' |   '|' |   '~' |   DIGIT   |   ALPHA;

headerName: token;
headerValue: token;

token: tchar+;

// JSON grammar
body: value;

object: '{' pair (',' pair)* '}' | '{' '}' ;

pair
    : STRING ':' value
    ;

array
    : '[' value (',' value)* ']'
    | '[' ']'
    ;

value
    : STRING
    | NUMBER
    | object
    | array
    | 'true'
    | 'false'
    | 'null'
    ;

STRING: '"' (ESC | SAFECODEPOINT)* '"' WS?;

fragment ESC: '\\' (["\\/bfnrt] | UNICODE);

fragment UNICODE: 'u' HEX HEX HEX HEX;

fragment HEX: [0-9a-fA-F];

fragment SAFECODEPOINT: ~ ["\\\u0000-\u001F];

NUMBER: '-'? INT ('.' [0-9]+)? EXP? ;

fragment INT: '0' | [1-9] [0-9]*;

fragment EXP: [Ee] [+-]? [0-9]+;

WS: [ \t\n\r]+ -> skip;