import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AppointmentWithEmployeeResponseDto } from 'src/app/models/appointment-with-employee-response-dto.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/ngbd-sortable-header.directive';
import { AppointmentDetailsDialogComponent } from './appointment-details-dialog/appointment-details-dialog.component';

const compare = (v1: any, v2:any) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);

@Component({
  selector: 'app-admin-appointment-list',
  templateUrl: './admin-appointment-list.component.html',
  styleUrls: ['./admin-appointment-list.component.css']
})
export class AdminAppointmentListComponent implements OnInit {
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  displayedColumns: string[] = ['id', 'appointmentDateTime', 'name', 'email', 'phoneNumber', 'service', 'paymentMethod', 'employee','appointmentStatus', 'actions'];
  dataSource: AppointmentWithEmployeeResponseDto[];
  initialDataSource: AppointmentWithEmployeeResponseDto[];

  constructor(private appointmentService: AppointmentService, private router: Router, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.appointmentService.getAllAppointments().subscribe(data => {
      this.dataSource = data;
      this.initialDataSource = data;
    });
  }

  openDialog(appointment: AppointmentWithEmployeeResponseDto) {
    this.dialog.open(AppointmentDetailsDialogComponent, {
      data: appointment
    });
  }

	onSort({ column, direction }: SortEvent) {
		// resetting other headers
		this.headers.forEach((header) => {
			if (header.sortable !== column) {
				header.direction = '';
			}
		});

		// sorting categories
		if (direction === '' || column === '') {
			this.dataSource = this.initialDataSource;
		} else {
			this.dataSource = [...this.initialDataSource].sort((a, b) => {
				const res = compare(a[column], b[column]);
				return direction === 'asc' ? res : -res;
			});
		}
	}
  

  delete(id: any) {
    this.appointmentService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting appointment");
    });
  }

  isAfterToday(date: Date): boolean {
    const today = new Date();
    return new Date(date) > today;
  }

  async pay(appointmentResponseDto: AppointmentWithEmployeeResponseDto) {
    var paramsMap = new Map();
    paramsMap.set("key", "c1L9yX1yrEEkQVGu2fBz5hImz");

    var form = document.createElement("form");
    form.method = "POST";
    form.action = "https://test-wallet.corvuspay.com/checkout/";
    form.style.display = 'none';

    let storeId = document.createElement("input");
    storeId.name = 'store_id';
    storeId.value = '13660';
    form.appendChild(storeId);
    paramsMap.set(storeId.name, storeId.value);

    let orderNumber = document.createElement("input");
    orderNumber.name = 'order_number';
    orderNumber.value = appointmentResponseDto.id + '-test';
    form.appendChild(orderNumber);
    paramsMap.set(orderNumber.name, orderNumber.value);

    let lang = document.createElement("input");
    lang.name = 'language';
    lang.value = 'en';
    form.appendChild(lang);
    paramsMap.set(lang.name, lang.value);

    let currency = document.createElement("input");
    currency.name = 'currency';
    currency.value = 'EUR';
    form.appendChild(currency);
    paramsMap.set(currency.name, currency.value);

    let amount = document.createElement("input");
    amount.name = 'amount';
    amount.value = appointmentResponseDto.service?.price?.toString();
    form.appendChild(amount);
    paramsMap.set(amount.name, amount.value);

    let cart = document.createElement("input");
    cart.name = 'cart';
    cart.value = appointmentResponseDto.service.name;
    form.appendChild(cart);
    paramsMap.set(cart.name, cart.value);

    let requireComplete = document.createElement("input");
    requireComplete.name = 'require_complete';
    requireComplete.value = 'false';
    form.appendChild(requireComplete);
    paramsMap.set(requireComplete.name, requireComplete.value);

    let version = document.createElement("input");
    version.name = 'version';
    version.value = '1.4';
    form.appendChild(version);
    paramsMap.set(version.name, version.value);

    let cardholderName = document.createElement("input");
    cardholderName.name = 'cardholder_name';
    cardholderName.value = appointmentResponseDto.name.split(" ")[0];
    form.appendChild(cardholderName);
    paramsMap.set(cardholderName.name, cardholderName.value);

    let cardholderSurname = document.createElement("input");
    cardholderSurname.name = 'cardholder_surname';
    cardholderSurname.value = appointmentResponseDto.name.split(" ")[1];
    form.appendChild(cardholderSurname);
    paramsMap.set(cardholderSurname.name, cardholderSurname.value);

    let cardholderPhone = document.createElement("input");
    cardholderPhone.name = 'cardholder_phone';
    cardholderPhone.value = appointmentResponseDto.phoneNumber;
    form.appendChild(cardholderPhone);
    paramsMap.set(cardholderPhone.name, cardholderPhone.value);

    let cardholderEmail = document.createElement("input");
    cardholderEmail.name = 'cardholder_email';
    cardholderEmail.value = appointmentResponseDto.email;
    form.appendChild(cardholderEmail);
    paramsMap.set(cardholderEmail.name, cardholderEmail.value);

    let calculatedSignature = await this.appointmentService.calculateSignature(paramsMap);

    let signature = document.createElement("input");
    signature.name = 'signature';
    if (calculatedSignature)
      signature.value = calculatedSignature;
    form.appendChild(signature);

    document.body.appendChild(form);
    form.submit();
  }
}
