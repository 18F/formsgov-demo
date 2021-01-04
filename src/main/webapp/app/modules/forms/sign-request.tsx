import React from 'react';
import Iframe from 'react-iframe';

const SignRequest = embedUrl => {
  // tslint:disable-next-line
  //   const signRequestUrl = `<iframe src=${embedUrl.toString()} title="Sign" allow="encrypted-media" style="width:100%; height:500px; border:0; border-radius: 4px; overflow:hidden;" sandbox="allow-modals allow-forms allow-popups allow-scripts allow-same-origin"></iframe>`;
  console.log('embedUrl url ***** ' + embedUrl);
  return <Iframe url={embedUrl} width="1000px" height="100px" display="block" position="relative" />;
};
export default SignRequest;
