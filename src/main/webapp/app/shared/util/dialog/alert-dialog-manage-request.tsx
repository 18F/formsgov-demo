import './dialog.scss';
import React from 'react';
import Dialog from '@material-ui/core/Dialog';
import { Col } from 'reactstrap';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Grid from '@material-ui/core/Grid';
import { Redirect } from 'react-router-dom';

let checkedValue = '';
const optionValidationText = 'Please select the options.';
export interface IAlertDialogProps {
  alertOpen: boolean;
  onClose: any;
  handleYes: any;
  fromHome: boolean;
  selectedDua: string;
  dataSource: any;
  copyStr: string;
  alertText: string;
  fromWhere: string;
  userId: string;
}

class AlertDialog extends React.Component<IAlertDialogProps> {
  state = {
    alertOpen: false,
    toNewRequestLanding: false,
    selectedDua: '',
    copyStr: '',
    alertText: '',
    toMangeSummary: false,
    finalRequestId: '',
    finalSuperId: '',
    fromWhere: '',
    showValidation: false,
    disableOkBtn: true
  };

  handleClose = () => {
    checkedValue = '';
    this.setState({ toNewRequestLanding: true, disableOkBtn: true });
    this.props.onClose();
  };
  handleOk = () => {
    if (checkedValue !== '') {
      checkedValue === 'signout'
        ? this.handleCancel(this.props.alertText)
        : this.handleSummary(this.props.selectedDua, this.props.selectedDua);
    } else {
      this.setState({ showValidation: true });
    }
  };
  handleCancel = val => {
    this.props.handleYes(val);
  };
  handleSummary = (superIdValue, requestIdValue) => {
    this.setState({ toMangeSummary: true });
    this.setState({ finalRequestId: requestIdValue });
    this.setState({ finalSuperId: superIdValue });
  };
  handleSignOutAndSummary = val => {
    this.setState({ showValidation: false, disableOkBtn: false });
    checkedValue = val.target.value;
  };
  render() {
    const { onClose, alertOpen, fromWhere, selectedDua, dataSource, copyStr, alertText, userId } = this.props;
    const { toMangeSummary } = this.state;
    if (toMangeSummary) {
      return (
        <Redirect
          to={{
            pathname: '/manage-request-summary/' + selectedDua,
            state: { summaryRequestId: selectedDua, summarySuperId: selectedDua, sendFrom: fromWhere, userId }
          }}
        />
      );
    }
    return (
      <React.Fragment>
        <Dialog
          maxWidth="md"
          open={alertOpen}
          onClose={onClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">
            <Grid container>
              <Grid item xs={10}>
                {fromWhere === 'manage request' ? (
                  <h1 className="ds-h2" id="dialog-title">
                    {alertText} Request
                  </h1>
                ) : (
                  <h1 className="ds-h2" id="dialog-title">
                    Signout/Summary
                  </h1>
                )}
              </Grid>
            </Grid>
          </DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              <div className="body-text">
                {fromWhere === 'manage request' ? (
                  <p className="ds-text">Are you sure you want to {copyStr} this request?</p>
                ) : (
                  <p className="ds-text">Please choose action. </p>
                )}
              </div>
              <aside className="ds-c-dialog__actions" role="complementary">
                {this.props.alertText === 'Summary/SignOut' ? (
                  <span>
                    <input
                      className="usa-radio__input"
                      onClick={value => {
                        this.handleSignOutAndSummary(value);
                      }}
                      id="signout"
                      type="radio"
                      name="historical-figures-2"
                      value="signout"
                    />
                    <label
                      aria-label="signout radio selection"
                      className="usa-radio__label"
                      style={{ color: 'black', marginBottom: '20px' }}
                      htmlFor="signout"
                    >
                      Signout
                    </label>
                    <input
                      className="usa-radio__input"
                      id="summary"
                      onClick={value => {
                        this.handleSignOutAndSummary(value);
                      }}
                      type="radio"
                      name="historical-figures-2"
                      value="summary"
                    />
                    <label aria-label="summary radio selection" className="usa-radio__label" style={{ color: 'black' }} htmlFor="summary">
                      Summary{' '}
                    </label>
                    <button
                      className="mr-5 ds-c-button ds-c-button--primary"
                      style={{ width: '100px', marginTop: '30px' }}
                      value={copyStr}
                      disabled={this.state.disableOkBtn}
                      onClick={() => {
                        this.handleOk();
                      }}
                    >
                      Ok
                    </button>
                  </span>
                ) : (
                  ''
                )}
                {this.props.alertText !== 'Summary/SignOut' ? (
                  <span>
                    <button
                      autoFocus
                      className="mr-5 ds-c-button ds-c-button--primary"
                      style={{ width: '100px' }}
                      value={copyStr}
                      onClick={() => {
                        this.handleCancel(alertText);
                      }}
                    >
                      Yes
                    </button>
                  </span>
                ) : (
                  ''
                )}
                <button
                  className="ml-5 ds-c-button ds-c-button--primary"
                  value={selectedDua}
                  style={{ width: '100px' }}
                  onClick={() => {
                    this.handleClose();
                  }}
                >
                  Cancel
                </button>
              </aside>
              <Col md="8" className={this.state.showValidation ? 'ds-c-alert ds-c-alert--error' : 'hide'}>
                <div className="ds-c-alert__body">
                  <p className="ds-c-alert__heading">{optionValidationText}</p>
                </div>
              </Col>
            </DialogContentText>
          </DialogContent>
        </Dialog>
      </React.Fragment>
    );
  }
}
export default AlertDialog;
