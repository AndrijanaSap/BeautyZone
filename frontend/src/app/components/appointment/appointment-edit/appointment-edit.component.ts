import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatChipOption } from '@angular/material/chips';
import { MatAccordion } from '@angular/material/expansion';
import { ActivatedRoute, Router } from '@angular/router';
import { data } from 'jquery';
import * as moment from 'moment';
import { AppointmentWithEmployeeResponseDto } from 'src/app/models/appointment-with-employee-response-dto.model';
import { AppointmentRequestDto } from 'src/app/models/appointmentRequest.model';
import { AvailabilityResponseDto } from 'src/app/models/availabilityResponse.model';
import { CategoryWithServicesDto } from 'src/app/models/category-with-services.model';
import { CombinationDto } from 'src/app/models/combination.model';
import { CreateUpdateAppointmentRequestDto } from 'src/app/models/create-update-appointment-request-dto.model';
import { DaySlot } from 'src/app/models/daySlot.model';
import { EmployeeDto } from 'src/app/models/employee.model';
import { TimeSlot } from 'src/app/models/timeSlot.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { CategoryService } from 'src/app/services/category.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { TimeslotService } from 'src/app/services/timeslot.service';


@Component({
  selector: 'app-appointment-edit',
  templateUrl: './appointment-edit.component.html',
  styleUrls: ['./appointment-edit.component.css']
})
export class AppointmentEditComponent {
  print(event: any) {
    console.log(event, 'event');
  }
  print2(event: any) {
    if (this.prev) {
      this.prev.deselect();
    }
    this.prev = event.source;
    this.appointmentForm.controls.appointment.setValue(this.prev.value);
    console.log(event, '2');
  }
  @ViewChild(MatAccordion) accordion: MatAccordion;
  prev: MatChipOption;
  availableTimeSlots: AvailabilityResponseDto[];
  daySlots: DaySlot[] = [];
  timeSlots: TimeSlot[];
  categories: CategoryWithServicesDto[];
  employees: EmployeeDto[];
  minDate: Date;
  maxDate: Date;
  appointment: AppointmentWithEmployeeResponseDto;
  appointmentsForm = new FormGroup({
    service: new FormControl(0, [
      Validators.required
    ]),
    employee: new FormControl(0),
    range: new FormGroup({
      start: new FormControl<Date | null>(null, Validators.required),
      end: new FormControl<Date | null>(null, Validators.required),
    })
  });
  appointmentForm = this.formBuilder.group({
    appointment: [new CombinationDto(), Validators.required],
  });
  personalInfoForm = new FormGroup({
    name: new FormControl('', [
      Validators.required
    ]),
    phoneNumber: new FormControl('', [
      Validators.required
    ]),
    email: new FormControl('', [
      Validators.required
    ]),
    note: new FormControl(''),
  });


  constructor(
    private categoryService: CategoryService,
    private employeeService: EmployeeService,
    private appointmentService: AppointmentService,
    private timeslotService: TimeslotService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder) {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDate = new Date().getDate();

    this.minDate = new Date(currentYear, currentMonth, currentDate);
    this.maxDate = new Date(currentYear + 1, currentMonth, currentDate);
  }

  async ngOnInit(): Promise<void> {
    this.categoryService.getAllCategoriesWithServices().subscribe(data => {
      this.categories = data;
    });

    const appointmentId = this.route.snapshot.paramMap.get('id');
    if (appointmentId) {
      this.appointmentService.getAppointmentById(appointmentId).subscribe(data => {
        this.appointment = data;
        // if (data.id) this.personalInfoForm.controls.id.setValue(data.id);
        if (data.employee.id) this.appointmentsForm.controls.employee.setValue(data.employee.id);
        if (data.service.id){
           this.appointmentsForm.controls.service.setValue(data.service.id);
           this.employeeService.getEmployeesByServiceId(data.service.id).subscribe(data => {
            this.employees = data;
          });
          }
        if (data.appointmentDateTime)
          { 
            this.appointmentsForm.controls.range.controls.start.setValue(new Date(data.appointmentDateTime));
            this.appointmentsForm.controls.range.controls.end.setValue(new Date(data.appointmentDateTime));
            this.appointmentsForm.controls.range.updateValueAndValidity();
          }
        this.onSubmitCheckAvailability();
        if (data.name) this.personalInfoForm.controls.name.setValue(data.name);
        if (data.note) this.personalInfoForm.controls.note.setValue(data.note);
        if (data.email) this.personalInfoForm.controls.email.setValue(data.email);
        if (data.phoneNumber) this.personalInfoForm.controls.phoneNumber.setValue(data.phoneNumber);
      })
    }

    this.appointmentsForm.valueChanges.subscribe((x:any) => {
      this.appointmentForm.reset();
      this.availableTimeSlots = [];
    });
  }


  onSubmitCheckAvailability() {
    console.log("form values: ", this.appointmentsForm);
    var dateFrom = this.appointmentsForm.controls.range.controls.start.value;
    var dateTo = this.appointmentsForm.controls.range.controls.end.value;
    var appointmentRequestDto: AppointmentRequestDto = new AppointmentRequestDto();

    if (dateFrom) {
      appointmentRequestDto.periodFrom = new Date(dateFrom.getFullYear(), dateFrom.getMonth(), dateFrom.getDate(), 0, 0, 0);
    }
    if (dateTo) {
      appointmentRequestDto.periodTo = new Date(dateTo.getFullYear(), dateTo.getMonth(), dateTo.getDate(), 23, 59, 59);
    }
    if (this.appointmentsForm.controls.service.value) {
      appointmentRequestDto.serviceId = this.appointmentsForm.controls.service.value;
    }

    if (this.appointmentsForm.controls.employee.value) {
      appointmentRequestDto.employeeId = this.appointmentsForm.controls.employee.value;
    }

    appointmentRequestDto.includeAppointmentId = this.appointment.id;

    this.timeslotService.checkAvailability(appointmentRequestDto).subscribe(data => {
      this.availableTimeSlots = data;
      // this.availableTimeSlots.forEach((availableTimeSlot) => {
      //   availableTimeSlot.combinationDtos.forEach((combinationDto) =>{
      //     if(combinationDto.startDateTime === this.appointment.appointmentDateTime)
      //       combinationDto.selected = true;
      //   });
      //   let timeSlots: TimeSlot[] = Object.entries(availableTimeSlot.combinationDtos.reduce((ac: { [key: string]: CombinationDto[] }, a) => {
      //     let key = moment(a.startDateTime).format('HH:mm');
      //     ac[key] = (ac[key] || []).concat(a);
      //     return ac;
      //   }, {})).map(([time, combinationDtos]) => ({ time, combinationDtos }));
      //   this.daySlots.push(new DaySlot(moment(availableTimeSlot.date).format('DD-mm'), timeSlots))
      // });
    });
  }

  onSubmitUpdateAppointment() {
    var updateDto: CreateUpdateAppointmentRequestDto = new CreateUpdateAppointmentRequestDto();

    if (this.appointmentForm.controls.appointment.value)
      updateDto.timeSlotIds = this.appointmentForm.controls.appointment.value.timeSlotIds;

      updateDto.id = this.appointment.id;

    if (this.appointmentsForm.controls.service.value)
      updateDto.serviceId = this.appointmentsForm.controls.service.value;

    if (this.appointmentsForm.controls.employee.value)
      updateDto.employeeId = this.appointmentsForm.controls.employee.value;

    if (this.personalInfoForm.controls.name.value)
      updateDto.name = this.personalInfoForm.controls.name.value;

    if (this.personalInfoForm.controls.phoneNumber.value)
      updateDto.phoneNumber = this.personalInfoForm.controls.phoneNumber.value;

    if (this.personalInfoForm.controls.email.value)
      updateDto.email = this.personalInfoForm.controls.email.value;

    updateDto.note = this.personalInfoForm.controls.note.value;

    this.appointmentService.updateAppointment(updateDto).subscribe(result => {
      if(result === true) {
        this.router.navigateByUrl('/appointments');
      }
    }

    );
  }

  private async redirectToPaymentGateway(appointmentResponseDto: AppointmentWithEmployeeResponseDto) {
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

  getEmployeesByServiceId(serviceId: number) {
    this.employeeService.getEmployeesByServiceId(serviceId).subscribe(data => {
      this.employees = data;
    });
  }
}
