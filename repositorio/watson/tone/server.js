var watson = require('watson-developer-cloud');

var tone_analyzer = watson.tone_analyzer({
  username: 'd310c982-f331-4c0c-a6ef-26d421ba8024',
  password: 'mhYgdlzeBbZt',
  version: 'v3-beta',
  version_date: '2016-02-11'
});

tone_analyzer.tone({ text: 'Hola que tal, soy el chico de las poesías y estoy super contento' },
  function(err, tone) {
    if (err)
      console.log(err);
    else
      console.log(JSON.stringify(tone, null, 2));
});
	