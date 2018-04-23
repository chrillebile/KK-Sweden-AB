import * as React from 'react';
import Table from 'Components/Table';
import { PalletActions } from 'app/actions/palletActions';
import { bindActionCreators, Dispatch } from 'redux';
import { connect } from 'react-redux';
import { RootState } from 'app/reducers';
import { omit } from 'app/utils';
import PalletSearchComponent from 'Components/PalletSearchComponent';
import Header from 'Containers/Header';
import { toast, ToastContainer } from 'react-toastify';

interface Props {
  actions: PalletActions,
  pallets: RootState.PalletState
}

@connect((state: RootState) => {
  return {
    pallets: state.pallets
  };
}, (dispatch: Dispatch<RootState>): Pick<Props, 'actions'> => ({
  actions: bindActionCreators(omit(PalletActions, 'Type'), dispatch)
}))

export class PalletSearch extends React.Component<Props> {
  componentWillMount() {
    // Update the list of pallets in state
    this.props.actions.getPallets();
  }

  render() {
    return (
      <div>
        <ToastContainer/>
        <Header/>
        <Table data={this.props.pallets.palletItems}/>
        <PalletSearchComponent actions={this.props.actions} resetPalletView={this.resetPalletView}/>
      </div>
    );
  }

  /**
   * Function to reset the pallet list to it's default state.
   */
  resetPalletView = (): void => {
    toast('Pallets have been reset.', {
      type: 'success'
    });
    this.props.actions.getPallets();
  };
}

export default PalletSearch;
