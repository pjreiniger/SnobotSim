

#pragma once

#ifndef EXPORT_
#ifdef _MSC_VER
#define EXPORT_ /*__declspec(dllexport)*/
#else
#define EXPORT_
#endif

#endif
