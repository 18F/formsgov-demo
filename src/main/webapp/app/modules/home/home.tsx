import './home.scss';
import React, { useState, useEffect } from 'react';
import http from '../../shared/service/http-service';
import { Form } from 'react-formio';
import uswds from '@formio/uswds';
import { Formio } from 'formiojs';
import LoadingOverlay from 'react-loading-overlay';
import { FadeLoader } from 'react-spinners';
Formio.use(uswds);
export const Home = () => {
  const [jwtToken, setJwtToken] = useState(0);
  const [submissionId, setSubmissionId] = useState(0);
  const [embedUrl, setEmbedUrl] = useState('');
  const [loader, setLoader] = useState(false);
  const [user, setUser] = useState({});
  useEffect(() => {
    login();
    getUser();
  }, []);
  const xAllow = `GET:/project/5f6a32fb7974387303dc6859/form/5fd14dd2ba8cc517f0ec74e4/submission/${submissionId}/download`;
  const requestData = {
    data: {
      email: 'service@gsa.gov',
      password: ''
    }
  };

  const handleOnSubmitDone = event => {
    setLoader(true);
    console.log('handleOnSubmitDone ' + event);
    getATokenKeyAndSign();
  };
  const handleOnSubmit = event => {
    setSubmissionId(event._id);
    console.log('handleOnSubmit', event);
  };

  const login = async () => {
    http.post('https://portal-dev.forms.gov/dev/admin/login', requestData).then(response => {
      setJwtToken(response.headers['x-jwt-token']);
      console.log(response.status);
    });
  };

  const getUser = async () => {
    const { data: response } = await http.get('api/user');
    console.log(response);
    setUser(response);
  };

  const getSignedRequest = async key => {
    const pdfUrl = 'https://portal-dev.forms.gov/dev/form/5fd14dd2ba8cc517f0ec74e4/submission/' + submissionId + '/download?token=' + key;
    const pdfName = 'F8821.pdf';
    const { data: response } = await http.get('api/sign', {
      params: {
        pdfUrl,
        pdfName
      }
    });
    console.log('response ***** ' + JSON.stringify(response));
    const embed_url = response.signers[0].embed_url;
    setEmbedUrl(embed_url);
    setLoader(false);
  };

  const getATokenKeyAndSign = async () => {
    console.log('jwtToken **** ' + jwtToken);
    http
      .get('https://portal-dev.forms.gov/dev/token', {
        headers: {
          'x-jwt-token': jwtToken,
          'x-allow': xAllow,
          'x-expire': 3600
        }
      })
      .then(resp => {
        console.log('Key ***** ' + resp.data['key']);
        getSignedRequest(resp.data['key']);
      });
  };
  const phone = user['phone'];
  const formattedPhone = phone !== undefined ? phone.replace(/\D+/g, '').replace(/^(\d{3})(\d{3})(\d{4}).*/, '($1) $2-$3') : '';
  const submissionData = {
    data: {
      taxpayerFirstName: user['firstName'],
      taxpayerLastName: user['lastName'],
      taxpayerHomeAddress: user['address1'],
      taxpayerCity: user['city'],
      taxpayerZip: user['zipcode'],
      taxpayerState: user['state'],
      taxpayerDaytimePhoneNumber: formattedPhone,
      taxpayerIdentificationNumber: user['ssn']
    }
  };
  return (
    <LoadingOverlay
      active={loader}
      styles={{ overlay: base => ({ ...base, background: 'rgba(0, 0, 0, 0.1)' }) }}
      spinner={<FadeLoader color={'#4A90E2'} />}
    >
      {embedUrl === '' ? (
        <div>
          <Form
            src="https://portal-dev.forms.gov/dev/irs8821"
            onSubmitDone={handleOnSubmitDone}
            onSubmit={handleOnSubmit}
            submission={submissionData}
          />
        </div>
      ) : (
        (window.location.href = embedUrl)
      )}
    </LoadingOverlay>
  );
};
export default Home;
