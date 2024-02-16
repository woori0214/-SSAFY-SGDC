import BackGroundMusic from "./BackGroundMusic.vue";
import PopUpMainMailbox from "../PopUp/PopUpMainMailbox.vue";
import { __VLS_internalComponent, __VLS_componentsOption, __VLS_name, userLoginStore, logout } from "./HeaderNav.vue";

function __VLS_template() {
let __VLS_ctx!: InstanceType<__VLS_PickNotAny<typeof __VLS_internalComponent, new () => {}>> & {};
/* Components */
let __VLS_otherComponents!: NonNullable<typeof __VLS_internalComponent extends { components: infer C; } ? C : {}> & typeof __VLS_componentsOption;
let __VLS_own!: __VLS_SelfComponent<typeof __VLS_name, typeof __VLS_internalComponent & (new () => { $slots: typeof __VLS_slots; })>;
let __VLS_localComponents!: typeof __VLS_otherComponents & Omit<typeof __VLS_own, keyof typeof __VLS_otherComponents>;
let __VLS_components!: typeof __VLS_localComponents & __VLS_GlobalComponents & typeof __VLS_ctx;
/* Style Scoped */
type __VLS_StyleScopedClasses = {} &
{ 'header-wrapper'?: boolean; } &
{ 'header-links'?: boolean; } &
{ 'login-signup-links'?: boolean; } &
{ 'nav-link'?: boolean; } &
{ 'nav-link'?: boolean; } &
{ 'user-nav'?: boolean; } &
{ 'logout'?: boolean; } &
{ 'mainimage-box'?: boolean; } &
{ 'header-image'?: boolean; } &
{ 'header-image'?: boolean; } &
{ 'header-music-box'?: boolean; } &
{ 'header-music'?: boolean; };
let __VLS_styleScopedClasses!: __VLS_StyleScopedClasses | keyof __VLS_StyleScopedClasses | (keyof __VLS_StyleScopedClasses)[];
/* CSS variable injection */
/* CSS variable injection end */
let __VLS_resolvedLocalAndGlobalComponents!: {} &
__VLS_WithComponent<'BackGroundMusic', typeof __VLS_localComponents, "BackGroundMusic", "BackGroundMusic", "BackGroundMusic"> &
__VLS_WithComponent<'RouterLink', typeof __VLS_localComponents, "RouterLink", "RouterLink", "RouterLink"> &
__VLS_WithComponent<'PopUpMainMailbox', typeof __VLS_localComponents, "PopUpMainMailbox", "PopUpMainMailbox", "PopUpMainMailbox">;
__VLS_intrinsicElements.header; __VLS_intrinsicElements.header;
__VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div; __VLS_intrinsicElements.div;
__VLS_components.BackGroundMusic; __VLS_components.BackGroundMusic;
// @ts-ignore
[BackGroundMusic, BackGroundMusic,];
__VLS_intrinsicElements.img;
__VLS_components.RouterLink; __VLS_components.RouterLink; __VLS_components.RouterLink; __VLS_components.RouterLink;
// @ts-ignore
[RouterLink, RouterLink, RouterLink, RouterLink,];
__VLS_intrinsicElements.span; __VLS_intrinsicElements.span;
__VLS_intrinsicElements.a; __VLS_intrinsicElements.a;
__VLS_components.PopUpMainMailbox;
// @ts-ignore
[PopUpMainMailbox,];
{
const __VLS_0 = __VLS_intrinsicElements["header"];
const __VLS_1 = __VLS_elementAsFunctionalComponent(__VLS_0);
const __VLS_2 = __VLS_1({ ...{}, class: ("header-wrapper"), }, ...__VLS_functionalComponentArgsRest(__VLS_1));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_0, typeof __VLS_2> & Record<string, unknown>) => void)({ ...{}, class: ("header-wrapper"), });
const __VLS_3 = __VLS_pickFunctionalComponentCtx(__VLS_0, __VLS_2)!;
let __VLS_4!: __VLS_NormalizeEmits<typeof __VLS_3.emit>;
{
const __VLS_5 = __VLS_intrinsicElements["div"];
const __VLS_6 = __VLS_elementAsFunctionalComponent(__VLS_5);
const __VLS_7 = __VLS_6({ ...{}, class: ("header-music-box"), }, ...__VLS_functionalComponentArgsRest(__VLS_6));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_5, typeof __VLS_7> & Record<string, unknown>) => void)({ ...{}, class: ("header-music-box"), });
const __VLS_8 = __VLS_pickFunctionalComponentCtx(__VLS_5, __VLS_7)!;
let __VLS_9!: __VLS_NormalizeEmits<typeof __VLS_8.emit>;
{
const __VLS_10 = ({} as 'BackGroundMusic' extends keyof typeof __VLS_ctx ? { 'BackGroundMusic': typeof __VLS_ctx.BackGroundMusic; } : typeof __VLS_resolvedLocalAndGlobalComponents).BackGroundMusic;
const __VLS_11 = __VLS_asFunctionalComponent(__VLS_10, new __VLS_10({ ...{}, class: ("header-music"), }));
({} as { BackGroundMusic: typeof __VLS_10; }).BackGroundMusic;
({} as { BackGroundMusic: typeof __VLS_10; }).BackGroundMusic;
const __VLS_12 = __VLS_11({ ...{}, class: ("header-music"), }, ...__VLS_functionalComponentArgsRest(__VLS_11));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_10, typeof __VLS_12> & Record<string, unknown>) => void)({ ...{}, class: ("header-music"), });
const __VLS_13 = __VLS_pickFunctionalComponentCtx(__VLS_10, __VLS_12)!;
let __VLS_14!: __VLS_NormalizeEmits<typeof __VLS_13.emit>;
}
(__VLS_8.slots!).default;
}
{
const __VLS_15 = __VLS_intrinsicElements["div"];
const __VLS_16 = __VLS_elementAsFunctionalComponent(__VLS_15);
const __VLS_17 = __VLS_16({ ...{}, class: ("mainimage-box"), }, ...__VLS_functionalComponentArgsRest(__VLS_16));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_15, typeof __VLS_17> & Record<string, unknown>) => void)({ ...{}, class: ("mainimage-box"), });
const __VLS_18 = __VLS_pickFunctionalComponentCtx(__VLS_15, __VLS_17)!;
let __VLS_19!: __VLS_NormalizeEmits<typeof __VLS_18.emit>;
{
const __VLS_20 = __VLS_intrinsicElements["img"];
const __VLS_21 = __VLS_elementAsFunctionalComponent(__VLS_20);
const __VLS_22 = __VLS_21({ ...{}, src: ("@/assets/mainimage.gif"), class: ("header-image"), }, ...__VLS_functionalComponentArgsRest(__VLS_21));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_20, typeof __VLS_22> & Record<string, unknown>) => void)({ ...{}, src: ("@/assets/mainimage.gif"), class: ("header-image"), });
const __VLS_23 = __VLS_pickFunctionalComponentCtx(__VLS_20, __VLS_22)!;
let __VLS_24!: __VLS_NormalizeEmits<typeof __VLS_23.emit>;
}
(__VLS_18.slots!).default;
}
{
const __VLS_25 = __VLS_intrinsicElements["div"];
const __VLS_26 = __VLS_elementAsFunctionalComponent(__VLS_25);
const __VLS_27 = __VLS_26({ ...{}, class: ("header-links"), }, ...__VLS_functionalComponentArgsRest(__VLS_26));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_25, typeof __VLS_27> & Record<string, unknown>) => void)({ ...{}, class: ("header-links"), });
const __VLS_28 = __VLS_pickFunctionalComponentCtx(__VLS_25, __VLS_27)!;
let __VLS_29!: __VLS_NormalizeEmits<typeof __VLS_28.emit>;
if (!__VLS_ctx.userLoginStore.loginUser) {
{
const __VLS_30 = __VLS_intrinsicElements["div"];
const __VLS_31 = __VLS_elementAsFunctionalComponent(__VLS_30);
const __VLS_32 = __VLS_31({ ...{}, class: ("login-signup-links"), }, ...__VLS_functionalComponentArgsRest(__VLS_31));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_30, typeof __VLS_32> & Record<string, unknown>) => void)({ ...{}, class: ("login-signup-links"), });
const __VLS_33 = __VLS_pickFunctionalComponentCtx(__VLS_30, __VLS_32)!;
let __VLS_34!: __VLS_NormalizeEmits<typeof __VLS_33.emit>;
{
const __VLS_35 = ({} as 'RouterLink' extends keyof typeof __VLS_ctx ? { 'RouterLink': typeof __VLS_ctx.RouterLink; } : typeof __VLS_resolvedLocalAndGlobalComponents).RouterLink;
const __VLS_36 = __VLS_asFunctionalComponent(__VLS_35, new __VLS_35({ ...{}, to: ("/login"), class: ("nav-link"), }));
({} as { RouterLink: typeof __VLS_35; }).RouterLink;
({} as { RouterLink: typeof __VLS_35; }).RouterLink;
const __VLS_37 = __VLS_36({ ...{}, to: ("/login"), class: ("nav-link"), }, ...__VLS_functionalComponentArgsRest(__VLS_36));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_35, typeof __VLS_37> & Record<string, unknown>) => void)({ ...{}, to: ("/login"), class: ("nav-link"), });
const __VLS_38 = __VLS_pickFunctionalComponentCtx(__VLS_35, __VLS_37)!;
let __VLS_39!: __VLS_NormalizeEmits<typeof __VLS_38.emit>;
(__VLS_38.slots!).default;
}
{
const __VLS_40 = ({} as 'RouterLink' extends keyof typeof __VLS_ctx ? { 'RouterLink': typeof __VLS_ctx.RouterLink; } : typeof __VLS_resolvedLocalAndGlobalComponents).RouterLink;
const __VLS_41 = __VLS_asFunctionalComponent(__VLS_40, new __VLS_40({ ...{}, to: ("/signup"), class: ("nav-link"), }));
({} as { RouterLink: typeof __VLS_40; }).RouterLink;
({} as { RouterLink: typeof __VLS_40; }).RouterLink;
const __VLS_42 = __VLS_41({ ...{}, to: ("/signup"), class: ("nav-link"), }, ...__VLS_functionalComponentArgsRest(__VLS_41));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_40, typeof __VLS_42> & Record<string, unknown>) => void)({ ...{}, to: ("/signup"), class: ("nav-link"), });
const __VLS_43 = __VLS_pickFunctionalComponentCtx(__VLS_40, __VLS_42)!;
let __VLS_44!: __VLS_NormalizeEmits<typeof __VLS_43.emit>;
(__VLS_43.slots!).default;
}
(__VLS_33.slots!).default;
}
// @ts-ignore
[userLoginStore,];
}
else {
{
const __VLS_45 = __VLS_intrinsicElements["div"];
const __VLS_46 = __VLS_elementAsFunctionalComponent(__VLS_45);
const __VLS_47 = __VLS_46({ ...{}, class: ("user-nav"), }, ...__VLS_functionalComponentArgsRest(__VLS_46));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_45, typeof __VLS_47> & Record<string, unknown>) => void)({ ...{}, class: ("user-nav"), });
const __VLS_48 = __VLS_pickFunctionalComponentCtx(__VLS_45, __VLS_47)!;
let __VLS_49!: __VLS_NormalizeEmits<typeof __VLS_48.emit>;
{
const __VLS_50 = __VLS_intrinsicElements["span"];
const __VLS_51 = __VLS_elementAsFunctionalComponent(__VLS_50);
const __VLS_52 = __VLS_51({ ...{}, }, ...__VLS_functionalComponentArgsRest(__VLS_51));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_50, typeof __VLS_52> & Record<string, unknown>) => void)({ ...{}, });
const __VLS_53 = __VLS_pickFunctionalComponentCtx(__VLS_50, __VLS_52)!;
let __VLS_54!: __VLS_NormalizeEmits<typeof __VLS_53.emit>;
(__VLS_ctx.userLoginStore.userNickname);
(__VLS_53.slots!).default;
}
{
const __VLS_55 = __VLS_intrinsicElements["a"];
const __VLS_56 = __VLS_elementAsFunctionalComponent(__VLS_55);
const __VLS_57 = __VLS_56({ ...{ onClick: {} as any, }, class: ("nav-link logout"), }, ...__VLS_functionalComponentArgsRest(__VLS_56));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_55, typeof __VLS_57> & Record<string, unknown>) => void)({ ...{ onClick: {} as any, }, class: ("nav-link logout"), });
const __VLS_58 = __VLS_pickFunctionalComponentCtx(__VLS_55, __VLS_57)!;
let __VLS_59!: __VLS_NormalizeEmits<typeof __VLS_58.emit>;
let __VLS_60 = { 'click': __VLS_pickEvent(__VLS_59['click'], ({} as __VLS_FunctionalComponentProps<typeof __VLS_56, typeof __VLS_57>).onClick) };
__VLS_60 = { click: (__VLS_ctx.logout) };
(__VLS_58.slots!).default;
}
(__VLS_48.slots!).default;
}
// @ts-ignore
[userLoginStore, logout,];
}
(__VLS_28.slots!).default;
}
{
const __VLS_61 = ({} as 'PopUpMainMailbox' extends keyof typeof __VLS_ctx ? { 'PopUpMainMailbox': typeof __VLS_ctx.PopUpMainMailbox; } : typeof __VLS_resolvedLocalAndGlobalComponents).PopUpMainMailbox;
const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({ ...{}, "<": (true), header: (true), }));
({} as { PopUpMainMailbox: typeof __VLS_61; }).PopUpMainMailbox;
const __VLS_63 = __VLS_62({ ...{}, "<": (true), header: (true), }, ...__VLS_functionalComponentArgsRest(__VLS_62));
({} as (props: __VLS_FunctionalComponentProps<typeof __VLS_61, typeof __VLS_63> & Record<string, unknown>) => void)({ ...{}, "<": (true), header: (true), });
const __VLS_64 = __VLS_pickFunctionalComponentCtx(__VLS_61, __VLS_63)!;
let __VLS_65!: __VLS_NormalizeEmits<typeof __VLS_64.emit>;
}
(__VLS_3.slots!).default;
}
if (typeof __VLS_styleScopedClasses === 'object' && !Array.isArray(__VLS_styleScopedClasses)) {
__VLS_styleScopedClasses["header-wrapper"];
__VLS_styleScopedClasses["header-music-box"];
__VLS_styleScopedClasses["header-music"];
__VLS_styleScopedClasses["mainimage-box"];
__VLS_styleScopedClasses["header-image"];
__VLS_styleScopedClasses["header-links"];
__VLS_styleScopedClasses["login-signup-links"];
__VLS_styleScopedClasses["nav-link"];
__VLS_styleScopedClasses["nav-link"];
__VLS_styleScopedClasses["user-nav"];
__VLS_styleScopedClasses["nav-link"];
__VLS_styleScopedClasses["logout"];
}
var __VLS_slots!: {};
return __VLS_slots;
}
