import * as React from 'react';
import Table from 'Components/Table';
import { PalletActions } from 'app/actions/palletActions';
import { bindActionCreators, Dispatch } from 'redux';
import { connect } from 'react-redux';
import { RootState } from 'app/reducers';
import { omit } from 'app/utils';

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
        <Table data={this.props.pallets.palletItems}/>
      </div>
    );
  }
}

export default PalletSearch;
