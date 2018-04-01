import * as React from 'react';
import PalletInterface from 'Models/PalletModel';
import Table from 'Components/Table';

interface Props {
}

export class PalletSearch extends React.Component<Props> {
  pallets: PalletInterface[] = [
    {
      amount: 0,
      productionDate: null,
      location: null,
      deliveryTime: null,
      blocked: false,
      id: 1,
    },
    {
      amount: 137,
      productionDate: null,
      location: null,
      deliveryTime: null,
      blocked: false,
      id: 2,
    },
  ];
  render() {
    return (
      <div>
        <Table data={this.pallets} />
      </div>
    );
  }
}

export default PalletSearch;
