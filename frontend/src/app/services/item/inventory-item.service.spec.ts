import { TestBed } from '@angular/core/testing';

import { InventoryItemService } from './inventory-item.service';

describe('InventoryItemService', () => {
  let service: InventoryItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InventoryItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
